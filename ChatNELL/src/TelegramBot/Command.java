/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramBot;

import Model.AnswerOrder;
import Model.User;
import Senders.TelegramSender;

/**
 *
 * @author wesley
 */
public class Command {
    public static final String START = "/start";
    public static final String HELP = "/help";
    
    public static void help(User user){
        String text = "I am the ChatNELL! A bot that answer questions using NELL's Knowledge Base.\n"
                + "My developers are upgrading me frequently. So, if I wont answer one question to you today, maybe I'll can do it in future.\n\n"
                + "If you want to see more about NELL's knowledge base, go to: http://rtw.ml.cmu.edu .";
        
        Senders.TelegramSender ts = new TelegramSender();
        ts.send(user, text);
    }
    
    
}
