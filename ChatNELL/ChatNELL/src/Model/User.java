/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author wesley
 */
public class User {
    
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String MESSAGE_INTERFACE = "interface";
    public static final String MESSAGE_HISTORY = "history";
    
    
    private String name;
    private String id;
    private String message_interface;
    private List<Message> message_history;
    
    public User(String name, String id, String message_interface){
        this.name = name;
        this.id = id;
        this.message_interface = message_interface;
        this.message_history = new ArrayList<Message>();
    }

    public User(JSONObject juser){
        this.name = (String) juser.get(NAME);
        this.id = (String) juser.get(ID);
        this.message_interface = (String) juser.get(MESSAGE_INTERFACE);
        this.message_history = JSONArrayToArrayList((com.mongodb.BasicDBList) juser.get(MESSAGE_HISTORY));
    }
    
    public JSONObject toJSON(){
        JSONObject juser = new JSONObject();
        juser.put(NAME,this.name);
        juser.put(ID, this.id);
        juser.put(MESSAGE_INTERFACE, this.message_interface);
        juser.put(MESSAGE_HISTORY, ArrayListToJSONArray(this.message_history));
        return juser;
    }
    
    private JSONArray ArrayListToJSONArray(List<Message> message_history){
        JSONArray jmh = new JSONArray();
        
        for(Message message: message_history){
            jmh.add(message.toJSON());
        }
        return jmh;
    }
    
    private List<Message> JSONArrayToArrayList(JSONArray jmessage_history){
        List<Message> message_history = new ArrayList<Message>();
        for(Object o: jmessage_history){
            Message message = new Message((JSONObject) o);
            message_history.add(message);
        }
        return message_history;
    }
    
    private List<Message> JSONArrayToArrayList( com.mongodb.BasicDBList jmessage_history){
        List<Message> message_history = new ArrayList<Message>();
        for(Object o: jmessage_history){
            JSONObject jo = new JSONObject((com.mongodb.BasicDBObject)o);
            Message message = new Message(jo);
            message_history.add(message);
        }
        return message_history;
    }
    
    
    public List<Message> getMessage_history() {
        return message_history;
    }

    public void setMessage_history(List<Message> message_history) {
        this.message_history = message_history;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_interface() {
        return message_interface;
    }

    public void setMessage_interface(String message_interface) {
        this.message_interface = message_interface;
    }
    
    
}
