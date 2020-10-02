/**
 * 
 */
package com.citi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.dto.MasterSecurityDTO;
import com.citi.entity.MasterSecurity;
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
	public List<MasterSecurityDTO> getMasterSecuritiesDTOList() {
		Iterable<MasterSecurity> masterSecurtiyList = masterSecurityRepository.findAll();
		List<MasterSecurityDTO> finalMasterSecurityList = new ArrayList<>();
		for(MasterSecurity masterSecurity : masterSecurtiyList) {
			MasterSecurityDTO masterSecurityDTO = new MasterSecurityDTO();
			masterSecurityDTO.setIsin(masterSecurity.getIsin());
			masterSecurityDTO.setIssuerName(masterSecurity.getIssuerName());
			masterSecurityDTO.setIssueDate(masterSecurity.getIssueDate());
			masterSecurityDTO.setMaturityDate(masterSecurity.getMaturityDate());
			masterSecurityDTO.setFaceValue(masterSecurity.getFaceValue());
			masterSecurityDTO.setSecurity(masterSecurity.getSecurity());
			masterSecurityDTO.setCouponRate(masterSecurity.getCouponRate());
			masterSecurityDTO.setDayCountConvention(masterSecurity.getDayCountConvention());
			finalMasterSecurityList.add(masterSecurityDTO);
		}
		return finalMasterSecurityList;
		
	}

}
