/**
 * 
 */
package com.citi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.entity.MasterSecurity;
import com.citi.main.CitiFixedIncomeApplication;
import com.citi.repository.MasterSecurityRepository;

/**
 * @author Dhruv
 *
 */

@Service
public class MasterSecurityService {
	Logger logger = LogManager.getLogger(MasterSecurityService.class.getName());
	
	@Autowired 
	MasterSecurityRepository masterSecurityRepository;

	public MasterSecurityRepository getMasterSecurityRepository() {
		return masterSecurityRepository;
	}

	public void setMasterSecurityRepository(MasterSecurityRepository masterSecurityRepository) {
		this.masterSecurityRepository = masterSecurityRepository;
	}
	
	@Transactional
	public void getMasterSecurities() {
		Iterable<MasterSecurity> masterSecurityList = masterSecurityRepository.findAll();
		
	}

}
