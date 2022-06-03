package org.mvc.helloworld.service;

import java.util.HashMap;

import org.mvc.helloworld.dto.User;

public class LoginService {
	HashMap<String, String> users = new HashMap<String, String>();
	
	public LoginService(){	
		//Dummy data instead of data from Data Source
		users.put("jamesgosling", "James Gosling");
		users.put("sunoracle", "Sun Oracle");
	}
	
	//Model: boolean //Business Service Method: authenticate
	public boolean authenticate(String userId, String password) {	
		if (password == null || password.trim().isEmpty()) {
			return false;			
		}
		return true;
	}
	
	//Model: User //Business Service Method: getUserDetails
	public User getUserDetails(String userId) {
		User usr = new User();
		usr.setUserName(users.get(userId));
		usr.setUserId(userId);		
		return usr;
	}
}
