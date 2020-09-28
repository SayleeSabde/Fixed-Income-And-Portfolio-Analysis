package com.citi.repository;

import org.springframework.data.repository.CrudRepository;

import com.citi.entity.Trade;

public interface TradeRepository extends CrudRepository<Trade, Integer> {

}
