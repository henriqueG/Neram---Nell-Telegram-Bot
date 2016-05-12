'use strict'
const http = require('http');
const Bot = require('messenger-bot');
const request = require('request');
const Wit = require('node-wit').Wit;

let bot = new Bot({
  token: 'EAADPq8H74aUBAGSpsF1riC0pZB7mXjelgCv9PQSSYsJuZCdMylT0jLsGSZCjKzGYdYCZAlXAu83LlRuvSSvtMahbTcNMW2tcrGjVjrBIUwsqJHzVSsWWQ0fmJ0ZBV5r4qBcheXnfPavThZACRCfCTCAPSMeZCo2COZAnIBlbXoxd2AZDZD',
  verify: 'PROJECT-ALPINE-IS-NELL-BOT'
});

// sessionId -> {fbid: facebookUserId, context: sessionState}
const sessions = {};

const findOrCreateSession = (fbid) => {
  console.log('creating session');

  let sessionId;

  // Let's see if we already have a session for the user fbid
  if (sessions !== null && Object.keys(sessions).length > 0) {
    Object.keys(sessions).forEach(k => {
      if (sessions[k].fbid === fbid) {
        // Yep, got it!
        sessionId = k;
        console.log('found');
      }
    });
  }

  if (!sessionId) {
    // No session found for user fbid, let's create a new one
    sessionId = new Date().toISOString();
    sessions[sessionId] = {fbid: fbid, context: {}, username:'unknown'};
    console.log('new session');
  }

  return sessionId;
};

const firstEntityValue = (entities, entity) => {
  const val = entities && entities[entity] &&
    Array.isArray(entities[entity]) &&
    entities[entity].length > 0 &&
    entities[entity][0].value
  ;
  if (!val) {
    return null;
  }
  return typeof val === 'object' ? val.value : val;
};

// Our bot actions
const actions = {
  say: (sessionId, context, message, cb) => {
    console.log('say action');

    if (sessions[sessionId] !== null) {
      const recipientId = sessions[sessionId].fbid;
      if (recipientId) {
        console.log('sending message');
        bot.sendMessage(recipientId, {text:message}, (err, body) => {

          if (err) {
              console.log(err.message);
          }

          cb();
        });
      }
    }    
  },
  merge: (sessionId, context, entities, message, cb) => {
    console.log(`merge action ${Object.keys(entities)} ${Object.keys(context)}`);

    const intent = firstEntityValue(entities, "intent");
    
    if (intent) { 
      delete context.intent;
      delete context.content;
      
      context.word = intent;
      cb(context);
    }
    else {
      delete context.word;
      delete context.intent;
      delete context.content;

      cb(context);
    }
  },
  error: (sessionId, context, error) => {
    console.log('error action');
    console.log(error.message);
  },
  'getUsername': (sessionId, context, cb) => {
    // Here should go the api call, e.g.:
    // context.forecast = apiCall(context.loc)
    if (sessions[sessionId] !== null) {
      console.log('get username action');
      const username = sessions[sessionId].username;
      if (username) {
        context.username = username;
      }
    }
    
    cb(context);
  },
  'getMeaning': (sessionId, context, cb) => {

    console.log('get meaning action');

    var nellUrl = `http://rtw.ml.cmu.edu/rtw/api/json0`;
    var nellProperties = {ent1:'', lit1:`${context.word}`, predicate:'*', ent2:'', lit2:'', agent:['KI', 'CKB', 'OPRA', 'OCMC'], format:'raw'};

    console.log(`lit1:${context.word}`);

    request({
      url: nellUrl,
      qs: nellProperties,
      json: true
    }, function(error, response, body) {

      delete context.content;

      if (!error) {
        var items = body.items;

        if (items) {
          if (Object.keys(items).length > 0) {
            var predicate = `${items[0].predicate}`;
            var score = items[0].justifications[0].score;

            //Gets the best NELL result
            Object.keys(items).forEach(k => {
              if (items[k].justifications[0].score > score) {
                // Yep, got it!
                predicate = items[k].predicate;
                score = items[k].justifications[0].score;
              }
            });

            context.content = predicate;
          }
        }
      }

      cb(context);
    });
  },
};

const wit = new Wit('SQPSRMO2QUJBWMCW4MUAZPM4QCUKOFZA', actions);

http.createServer(bot.middleware()).listen(8080);

bot.on('error', (err) => {
  console.log(err.message);
});

bot.on('message', (payload, reply) => {
  console.log('BOT ON!');

  bot.getProfile(payload.sender.id, (err, profile) => {
    if (err) throw err;

    const sender = payload.sender.id;
    const sessionId = findOrCreateSession(sender);

    if (sessions[sessionId] !== null) {
      sessions[sessionId].username = profile.first_name;

      wit.runActions(
        sessionId, // the user's current session
        payload.message.text, // the user's message 
        sessions[sessionId].context, // the user's current session state
        (err, context) => {
            if (err) {
              console.log('Oops! Got an error from Wit:', err);
            } else {
              console.log('Waiting for futher messages.');
              sessions[sessionId].context = context;
            }
      });
    }
    
  });
});

