package com.citi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.entity.MasterSecurity;

@Repository
public interface MasterSecurityRepository extends CrudRepository<MasterSecurity, String>{

}
