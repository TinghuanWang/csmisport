package com.tiedate.csmis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiedate.csmis.dao.TestDao;
import com.tiedate.csmis.data.TestUser;

@Service
@Transactional("transactionManagerJpa")
public class TestServices {
	
	@Autowired TestDao testDao;
	
	public void save(TestUser user){
		testDao.save(user);
		
	}

}
