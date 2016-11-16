/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelegramBot;

import ChattingManager.ChattingManager;
import Model.Login;
import Model.MessageInterface;
import Model.User;
import Senders.TelegramSender;
import java.time.LocalTime;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 *
 * @author wesley
 */
public class TelegramReceiver extends TelegramLongPollingBot{
    @Override
    public String getBotToken() {
        //return BotConfig.MARKET_TOKEN;
        return Login.TOKEN;
    }

    @Override
    public String getBotUsername() {
        return Login.USER;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        //Identificar o usuário
              String id = update.getMessage().getChatId().toString();
        
        User user;
        
        if(ChattingManager.exist(id,MessageInterface.TELEGRAM)){
            user = ChattingManager.getUser(id,MessageInterface.TELEGRAM);
        }else{
            String name = update.getMessage().getFrom().getFirstName();
            user = new User(name, id, MessageInterface.TELEGRAM);
            ChattingManager.newUser(user);
        }
        
        Message telegram_message = update.getMessage();
        
        
        System.out.println("\n"+LocalTime.now()+":[SweetMarket] "+user.getName()+": "+telegram_message.getText());
        
        if(telegram_message.isCommand()){
            switch(telegram_message.getText()){
                case Command.START:
                    break;
                case Command.HELP: 
                    Command.help(user);
                    break;
            }
        }else{
            //Armazenar Mensagem
            Model.Message message = new Model.Message(System.currentTimeMillis(), update.getMessage().getText());
            
            user.getMessage_history().add(message);
            
            ChattingManager.updateUser(user);
        }
        
    }
    
}
