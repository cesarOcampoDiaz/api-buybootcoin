package com.nttdata.router;


import com.nttdata.handler.BootCoinHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class BootCoinRouter {
    @Bean
    public RouterFunction<ServerResponse> transactionyankiRouterFunc(BootCoinHandler transactionHandler) {
        return RouterFunctions.route(POST("/buybootcoin").and(accept(MediaType.APPLICATION_JSON)), transactionHandler::add);


    }

}
