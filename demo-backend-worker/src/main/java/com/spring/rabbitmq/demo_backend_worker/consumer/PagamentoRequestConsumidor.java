package com.spring.rabbitmq.demo_backend_worker.consumer;

import com.spring.rabbitmq.demo_backend_worker.producer.PagamentoErroProdutor;
import com.spring.rabbitmq.demo_backend_worker.producer.PagamentoSucessoProdutor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Random;

@Component
public class PagamentoRequestConsumidor {
    @Autowired private PagamentoErroProdutor erroProdutor;
    @Autowired private PagamentoSucessoProdutor sucessoProdutor;

   @RabbitListener(queues = "pagamento-request-queue")
    public void receberMensagem(@Payload Message message) {
       System.out.println(message);
       if (new Random().nextBoolean()) {
           sucessoProdutor.gerarResposta(MessageFormat.format("Mensagem de sucesso Pagamento {0}", message));
       } else {
           erroProdutor.gerarResposta(MessageFormat.format("ERRO no pagamento {0}", message));
       }
    }
}
