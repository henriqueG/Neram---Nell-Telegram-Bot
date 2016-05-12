/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author wesley
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        String token = "189047198:AAGEuQrsvGQtp_2k49nZ4qnVobfARqCoKqM";
        System.out.println("[MAIN] Iniciando thread...");
        TelegramListener tarv = new TelegramListener();
        tarv.token = token;
        Thread t = new Thread(tarv);
        t.run();
            
            
        }
        
        
        
        
        
        
        
        
    
    
}
