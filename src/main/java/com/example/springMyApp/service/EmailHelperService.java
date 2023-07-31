package com.example.springMyApp.service;

import java.util.Map;

import com.example.springMyApp.dao.User;

public interface EmailHelperService {

	public boolean sendMail(User user);


	public String mailForForgotPassword(String name, String password, String message);

	public boolean sendMailForForgotPassword(String userName, String password,String email);
	


}
