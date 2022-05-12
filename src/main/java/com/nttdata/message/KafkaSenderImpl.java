package com.nttdata.message;

import com.nttdata.document.BootCoin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSenderImpl  implements  KafkaSender{

    private  final KafkaTemplate<String, BootCoin> kafkaTemplate;

    public KafkaSenderImpl(@Qualifier("kafkaJsonTemplate") KafkaTemplate<String, BootCoin> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sedMessage(String topic, BootCoin bootCoin)  {

        ListenableFuture<SendResult<String,BootCoin>> future = this.kafkaTemplate.send(topic,bootCoin);
        future.addCallback(new ListenableFutureCallback<SendResult<String, BootCoin>>() {
            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<String, BootCoin> result) {
                System.out.println("send "+result.getProducerRecord().value().getAmount()+" topic "+result.getRecordMetadata().topic());
            }
        });



    }
}
