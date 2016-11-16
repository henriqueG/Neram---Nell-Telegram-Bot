/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QAMonitor;

import Model.AnswerOrder;
import Senders.TelegramSender;

/**
 *
 * @author WesleyW
 */
public class QAMonitor implements Runnable {

    @Override
    public void run() {

        while (true) {

            //Busca a próxima mensagem não respondida
            AnswerOrder answer_order = ChattingManager.ChattingManager.nextMessage();

            if (answer_order != null) {
                String answer;

                //Entrega para o Wit.ai
                //Verifica se precisa de informações da KB da NELL
                //Chama o Zenodotus
                //Completa a resposta
                answer = "Hi " + answer_order.getUser_name() + "!";

                answer_order.getMessage().setAnswer(answer, System.currentTimeMillis());

                //Envia a resposta através do TelegramSender
                Senders.TelegramSender ts = new TelegramSender();
                ts.send(answer_order);
            }

        }

    }

}
