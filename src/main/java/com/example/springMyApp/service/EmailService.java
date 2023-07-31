package com.example.springMyApp.service;

import java.util.Map;

import com.example.springMyApp.responseModel.MailRequest;
import com.example.springMyApp.responseModel.MailResponse;



public interface EmailService {
	
	
	public MailResponse sendMail(MailRequest request, String html);
	
	public String convertDataToTemplate(String templateName,Map<String, Object> model);
	
}
