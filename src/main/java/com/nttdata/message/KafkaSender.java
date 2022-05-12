package com.nttdata.message;

import com.nttdata.document.BootCoin;

public interface KafkaSender {

    public void sedMessage(String topic, BootCoin bootCoin) ;
}
