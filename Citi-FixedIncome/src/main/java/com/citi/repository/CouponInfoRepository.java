/**
 * 
 */
package com.citi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.entity.CouponInfo;

/**
 * @author Dhruv
 *
 */

@Repository
public interface CouponInfoRepository extends CrudRepository<CouponInfo, Integer> {

}
