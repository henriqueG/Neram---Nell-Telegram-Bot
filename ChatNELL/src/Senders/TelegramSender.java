/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Senders;

import Model.AnswerOrder;
import Model.Login;
import Model.Status;
import Model.User;
import java.time.LocalTime;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TelegramSender extends TelegramLongPollingBot{
    
    @Override
    public String getBotToken() {
        //return BotConfig.MARKET_TOKEN;
        return Login.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        
    }

    @Override
    public String getBotUsername() {
        return Login.USER;
    }
    
    public void send(AnswerOrder answer_order){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(new ReplyKeyboardHide());
        
        sendMessage.setChatId(answer_order.getId());
        sendMessage.setText(answer_order.getMessage().getAnswer());
        boolean delivered = false;
        
        while(!delivered){
        try {
            sendMessage(sendMessage); //at the end, so some magic and send the message ;)
            delivered = true;
            answer_order.getMessage().setStatus(Status.ANSWERED);
            ChattingManager.ChattingManager.updateAnswer(answer_order);
        } catch (TelegramApiException e) {
            System.out.println("[elegramSender] Falha ao enviar... Tentando Novamente...");
        }
        if(!delivered){
            try{
            Thread.sleep(500);
            }catch(Exception e){
                System.out.println(LocalTime.now().toString()+" [TelegramSender] Erro na thread.");
            }
        }
        }
        
    }
    
    public void send(User user, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(new ReplyKeyboardHide());
        
        sendMessage.setChatId(user.getId());
        sendMessage.setText(text);
        boolean delivered = false;
        
        while(!delivered){
        try {
            sendMessage(sendMessage); //at the end, so some magic and send the message ;)
            delivered = true;
        } catch (TelegramApiException e) {
            System.out.println("[elegramSender] Falha ao enviar... Tentando Novamente...");
        }
        if(!delivered){
            try{
            Thread.sleep(500);
            }catch(Exception e){
                System.out.println(LocalTime.now().toString()+" [TelegramSender] Erro na thread.");
            }
        }
        }
        
    }
    
}
