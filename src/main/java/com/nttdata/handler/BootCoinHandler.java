package com.nttdata.handler;


import com.nttdata.document.BootCoin;
import com.nttdata.message.KafkaSender;
import com.nttdata.repository.BootCoinRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Component
public class BootCoinHandler {

    private final KafkaSender kafkaSender;

    private final BootCoinRepository transactionalRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    @Autowired
    public BootCoinHandler(KafkaSender kafkaSender, BootCoinRepository transactionalRepository) {
        this.kafkaSender = kafkaSender;
        this.transactionalRepository = transactionalRepository;
    }

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        var transactionMono = serverRequest.bodyToMono(BootCoin.class);

        String topic = "bootcoin";
        return transactionMono.flatMap(t -> {
                    t.setDateTransaction(LocalDateTime.now());
                    return ServerResponse.status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(transactionalRepository.save(t).map(x -> {
                                kafkaSender.sedMessage(topic, x);
                                return x;
                            }), BootCoin.class);
                }
        );
    }


}
