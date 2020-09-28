/**
 * 
 */
package com.citi.servicetest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.citi.entity.MasterSecurity;
import com.citi.repository.MasterSecurityRepository;

/**
 * @author Dhruv
 *
 */
class MasterSecurityServiceTest {
	
	@Autowired 
	MasterSecurityRepository masterSecurityRepository;

	@Test
	void test() {
		System.out.println("JUST TEST ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		MasterSecurity masterSecurity = new MasterSecurity();
		masterSecurity.setIsin("123456789012");
		masterSecurityRepository.save(masterSecurity);
		Iterable<MasterSecurity> slist = masterSecurityRepository.findAll();
		for (MasterSecurity s : slist) {
			System.out.println(s.getIsin());
		}
		System.out.println("Hello");
	}

}
