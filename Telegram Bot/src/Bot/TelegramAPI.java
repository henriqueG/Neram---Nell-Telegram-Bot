/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author wesley
 */
public class TelegramAPI {
    //URL
        public static final String URL = "https://api.telegram.org/bot";
    //General
        public static final String ID = "id";
    
        
    //Result
        public static final String RESULT = "result";
    //Update
        public static final String UPDATE_ID = "update_id";
        public static final String MESSAGE = "message";
        public static final String INLINE_QUERY = "inline_query";
        public static final String CHOSEN_INLINE_RESULT = "chosen_inline_result";
        public static final String CALLBACK_QUERY = "callback_query";
    //Message
        public static final String CHAT = "chat";
        public static final String MESSAGE_ID = "message_id";
        public static final String FROM = "from";
        public static final String TEXT = "text";
        
        
    //User
        public static final String FIRST_NAME = "first_name";
        
        
        
    
    
    
    public static JSONObject getMe(String token){
        String connection_url = URL+token+"/getMe";
        JSONObject jResult = null;
        
        try{
            String sResult = Jsoup.connect(connection_url).ignoreContentType(true).execute().body();
            JSONParser parser = new JSONParser();
            jResult = (JSONObject) parser.parse(sResult);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return jResult;
    }
    
    public static JSONObject getUpdates(String token){
        String connection_url = URL+token+"/getUpdates";
        
        JSONObject jResult = null;
        
        try{
            String sResult = Jsoup.connect(connection_url).ignoreContentType(true).execute().body();
            JSONParser parser = new JSONParser();
            jResult = (JSONObject) parser.parse(sResult);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return jResult;
    }
    
    public static JSONObject sendMessage(String token, String chat_id, String text){
        String connection_url = URL+token+"/sendmessage?chat_id="+chat_id+"&text="+text;
        JSONObject jResult = null;
        
        try{
            String sResult = Jsoup.connect(connection_url).ignoreContentType(true).execute().body();
            JSONParser parser = new JSONParser();
            jResult = (JSONObject) parser.parse(sResult);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return jResult;
    }
    
}
