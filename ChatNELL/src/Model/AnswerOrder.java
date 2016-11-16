/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author WesleyW
 */
public class AnswerOrder {
    private Message message;
    private String user_name;
    private String message_interface;
    private String id;
    
    public AnswerOrder(String id, String user_name, String message_interface, Message message){
        this.id = id;
        this.user_name = user_name;
        this.message_interface = message_interface;
        this.message = message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getMessage_interface() {
        return message_interface;
    }

    public String getId() {
        return id;
    }
    
    
}
