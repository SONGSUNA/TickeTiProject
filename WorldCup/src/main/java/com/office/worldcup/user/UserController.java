package com.office.worldcup.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/user/**")
@Log4j2
public class UserController {
	@Autowired
	UserService userService;
	
}
