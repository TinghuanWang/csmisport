package com.tiedate.csmis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiedate.csmis.data.TestUser;

@Repository
public interface TestDao extends JpaRepository<TestUser, Long>{

}
