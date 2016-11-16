/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import org.json.simple.JSONObject;

/**
 *
 * @author WesleyW
 */
public class Message {
    public static final String QUESTION_TIME = "question_time";
    public static final String QUESTION = "question";
    public static final String ANSWER_TIME = "answer_time";
    public static final String ANSWER = "answer";
    public static final String STATUS = "status";       
    
    private long question_time;
    private String question;
    
    private String answer;
    private long answer_time;
    
    private String status;
    
    public Message(long question_time, String question){
        this.question = question;
        this.question_time = question_time;
        
        this.answer = "";
        this.answer_time = 0;
        
        this.status = Status.RECIVED;
    }
    
    public Message(JSONObject jmessage){
        this.question = (String) jmessage.get(QUESTION);
        this.question_time = (long) jmessage.get(QUESTION_TIME);
        this.answer = (String) jmessage.get(ANSWER);
        this.answer_time = (long) jmessage.get(ANSWER_TIME);
        this.status = (String) jmessage.get(STATUS);
    }
    
    public JSONObject toJSON(){
        JSONObject jmessage = new JSONObject();
        jmessage.put(QUESTION,this.question);
        jmessage.put(QUESTION_TIME, this.question_time);
        jmessage.put(ANSWER, this.answer);
        jmessage.put(ANSWER_TIME, this.answer_time);
        jmessage.put(STATUS, this.status);
        return jmessage;
    }

    public long getQuestion_time() {
        return question_time;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public long getAnswer_time() {
        return answer_time;
    }

    public String getStatus() {
        return status;
    }
    

    

    public void setAnswer(String answer, long answer_time) {
        this.answer = answer;
        this.answer_time = answer_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
  
    
}
