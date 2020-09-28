/**
 * 
 */
package com.citi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.entity.MarketPrice;
/**
 * @author Dhruv
 *
 */
@Repository
public interface MarketPriceRepository extends CrudRepository<MarketPrice, String>  {

}
