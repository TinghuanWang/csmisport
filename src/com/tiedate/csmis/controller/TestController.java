package com.tiedate.csmis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiedate.csmis.data.TestUser;
import com.tiedate.csmis.services.TestServices;

@Controller
@RequestMapping(value = "/system")
public class TestController {
	
	@Autowired TestServices testservices;

	@RequestMapping("login")
	public String getTest(TestUser user, Model model) {

		testservices.save(user);
		System.out.println("--------------" + user.getName());
		return "welcome";

	}

}
