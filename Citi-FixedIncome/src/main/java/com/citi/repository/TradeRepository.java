package com.citi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.entity.Trade;

@Repository
public interface TradeRepository extends CrudRepository<Trade, String> {

}
