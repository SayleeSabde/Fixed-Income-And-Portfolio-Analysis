/**
 * 
 */
package com.citi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.citi.dto.CouponInfoDTO;
import com.citi.entity.CouponInfo;
import com.citi.repository.CouponInfoRepository;

/**
 * @author Dhruv
 *
 */
public class CouponInfoService {
	
	@Autowired
	CouponInfoRepository couponInfoRepository;

	public CouponInfoRepository getCouponInfoRepository() {
		return couponInfoRepository;
	}

	public void setCouponInfoRepository(CouponInfoRepository couponInfoRepository) {
		this.couponInfoRepository = couponInfoRepository;
	}
	
	@Transactional
	public Iterable<CouponInfoDTO> getCouponList() {
		Iterable<CouponInfo> couponsList = couponInfoRepository.findAll();
		List<CouponInfoDTO> couponDTOList = new ArrayList<>();
		for(CouponInfo coupon : couponsList) {
			CouponInfoDTO couponDTO = new CouponInfoDTO();
			couponDTO.setId(coupon.getId());
			couponDTO.setIsin(coupon.getIsin());
			couponDTO.setFaceValue(coupon.getFaceValue());
			couponDTO.setCouponRate(coupon.getCouponRate());
			couponDTO.setCouponDates(coupon.getCouponDates());
			couponDTO.setDayCountConvention(coupon.getDayCountConvention());
			couponDTOList.add(couponDTO);
		}
		return couponDTOList;
		
	}
	
	@Transactional
	public double getDccFactor(String dcc) {
		String dccSplit[] = dcc.split("_");
		List<String> dccSplitList = new ArrayList<String>();
		dccSplitList = Arrays.asList(dccSplit);
		String n = dccSplitList.get(0);
		double numerator = 0;
		if(n.equals("30")) {
			numerator = 30;
		}
		else {
			numerator = 365;
		}
		int d = Integer.parseInt(dccSplitList.get(1));
		double factor = numerator/d;
		return factor;
		
	}

}
