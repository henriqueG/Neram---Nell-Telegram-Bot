/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot;

import Utilities.FileUtilities;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author wesley
 */
public class TelegramListener implements Runnable {

    public String token;
    JSONObject me;

    @Override
    public void run() {

        if (iniciar()) {
            System.out.println("[" + me.get(TelegramAPI.FIRST_NAME).toString() + "] Olá mundo!");
            int lastUpdate = 0;
            JSONArray updates = (JSONArray) TelegramAPI.getUpdates(token).get(TelegramAPI.RESULT);
            JSONObject update;
            
            if(updates.size()>0){
                update = (JSONObject) updates.get(updates.size() - 1);
                lastUpdate = Integer.parseInt(update.get(TelegramAPI.UPDATE_ID).toString());
            }
            
            
            
            while (true) {
                try {
                    updates = (JSONArray) TelegramAPI.getUpdates(token).get(TelegramAPI.RESULT);
                    if (updates.size() > 0) {

                        for (int i = 0; i < updates.size(); i++) {
                            update = (JSONObject) updates.get(i);

                            if (lastUpdate < Integer.parseInt(update.get(TelegramAPI.UPDATE_ID).toString())) {
                                JSONObject message = (JSONObject) update.get(TelegramAPI.MESSAGE);
                                System.out.println("[" + me.get(TelegramAPI.FIRST_NAME).toString() + "] Nova mensagem recebida!");
                                interpretar(message);
                                lastUpdate = Integer.parseInt(update.get(TelegramAPI.UPDATE_ID).toString());
                            }

                        }

                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TelegramListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("[THREAD] Este bot ainda não foi configurado, Entre com o token no arquivo de configutação");
        }
    }

    private void interpretar(JSONObject message) {
        JSONObject chat = (JSONObject) message.get(TelegramAPI.CHAT);
        JSONObject user = (JSONObject) message.get(TelegramAPI.FROM);

        String chat_id = chat.get(TelegramAPI.ID).toString();

        String text = "Olá " + user.get(TelegramAPI.FIRST_NAME).toString() + "!";

        TelegramAPI.sendMessage(token, chat_id, text);
        System.out.println("[" + me.get(TelegramAPI.FIRST_NAME).toString() + "] Mensagem respondida.");
    }

    private boolean iniciar() {
        boolean iniciado = false;
        System.out.println("[THREAD] Verificando arquivos de configuração...");
        try {

            if (FileUtilities.mkdir("data")) {
                System.out.println("[THREAD] Criando arquivo de configuração...");
                JSONObject config = new JSONObject();
                config.put("token", "<token>");
                FileUtilities.save(config, "data/config.json");
                iniciado = false;

            } else {

                JSONArray arquivos = FileUtilities.listFiles("/data/");
                if (arquivos.size() <= 0) {
                    System.out.println("[THREAD] Carregando arquivo de configuração...");
                    JSONObject arquivo = FileUtilities.load("data/config.json");
                    if (arquivo.get("token").toString().equals("<token>")) {
                        System.out.println("[THREAD] Token não encontrado.");
                        iniciado = false;
                    } else {
                        token = arquivo.get("token").toString();
                        JSONObject result = TelegramAPI.getMe(token);
                        me = (JSONObject) result.get("result");
                        iniciado = true;
                    }
                } else {
                    System.out.println("[THREAD] Criando arquivo de configuração...");
                    JSONObject config = new JSONObject();
                    config.put("token", "<token>");
                    FileUtilities.save(config, "data/config.json");
                    iniciado = false;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iniciado;
    }

}
