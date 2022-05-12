package com.nttdata.repository;

import com.nttdata.document.BootCoin;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BootCoinRepository extends ReactiveMongoRepository<BootCoin,String> {

}
