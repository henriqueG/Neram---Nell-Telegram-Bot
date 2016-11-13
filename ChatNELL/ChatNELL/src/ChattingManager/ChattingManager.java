/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChattingManager;

import Model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Map;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author WesleyW
 */
public class ChattingManager {
    
    public static User getUser(String id, String message_interface){
        //Conecção
        MongoClient mongoClient = new MongoClient(Connection.URL , Connection.PORT);
        DB db = mongoClient.getDB(Connection.DATABASE);
        DBCollection collection = db.getCollection(Connection.COLLECTION);
        
        //Query
        BasicDBObject query = new BasicDBObject();
        query.put(User.ID, id);
        query.put(User.MESSAGE_INTERFACE, message_interface);
        
        //Recuperando informações
        JSONObject jUser;
        try{
            jUser = new JSONObject((Map) collection.findOne(query));
        }catch(Exception e){
            jUser = null;
            System.out.println("[ChattingManager] id "+ id +" not found!");
        }
        
        User user = new User(jUser);
        
        return user;
    }
    
    public static boolean exist(String id, String message_interface){
        MongoClient mongoClient = new MongoClient(Connection.URL , Connection.PORT);
        DB db = mongoClient.getDB(Connection.DATABASE);
        DBCollection collection = db.getCollection(Connection.COLLECTION);
        
        BasicDBObject query = new BasicDBObject();
        query.put(User.ID, id);
        query.put(User.MESSAGE_INTERFACE, message_interface);
        
        JSONObject jUser;
        try{
            jUser = new JSONObject((Map) collection.findOne(query));
        }catch(Exception e){
            jUser = null;
        }
        
        if(jUser == null){
            return false;
        }else if(((String) jUser.get(User.ID)).equals(id)){
            return true;
        }else{
            return false;
        }
        
    }
    
    public static void newUser(User user){
        MongoClient mongoClient = new MongoClient(Connection.URL , Connection.PORT);
        DB db = mongoClient.getDB(Connection.DATABASE);
        DBCollection collection = db.getCollection(Connection.COLLECTION);
        
        if(!exist(user.getId(), user.getMessage_interface())){
            BasicDBObject dbUser = new BasicDBObject(user.toJSON());
            try{
                collection.insert(dbUser);
            }catch(Exception e){
                System.out.println("[ChattingManager] Error while saving!");
            }
        }else{
            System.out.println("[ChattingManager] User with same id found!");
        }
        
    }
    
    public static void updateUser(User user){
        MongoClient mongoClient = new MongoClient(Connection.URL , Connection.PORT);
        DB db = mongoClient.getDB(Connection.DATABASE);
        DBCollection collection = db.getCollection(Connection.COLLECTION);
        
        BasicDBObject query = new BasicDBObject();
        query.put("id",user.getId());
        query.put("interface",user.getMessage_interface());
        
        BasicDBObject dbUser = new BasicDBObject(user.toJSON());
        
        collection.update(query, dbUser);
    }
    
    public static void removeUser(String id){
        MongoClient mongoClient = new MongoClient(Connection.URL , Connection.PORT);
        DB db = mongoClient.getDB(Connection.DATABASE);
        DBCollection collection = db.getCollection(Connection.COLLECTION);
        
        BasicDBObject query = new BasicDBObject();
        query.put(User.ID,id);
        
        collection.remove(query);
        
    }
    
    
}
