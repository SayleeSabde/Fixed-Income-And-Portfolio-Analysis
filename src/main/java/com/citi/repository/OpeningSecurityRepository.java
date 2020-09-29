package com.citi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.citi.entity.OpeningSecurity;

@Repository
public interface OpeningSecurityRepository extends CrudRepository<OpeningSecurity, String> {

}
