package com.example.springMyApp.serviceImpl;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;    
import javax.mail.internet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.springMyApp.dao.User;
import com.example.springMyApp.service.EmailHelperService;

 
@Service
public class EmailHelperServiceImpl implements EmailHelperService {

	
	
	private static final Logger logger = LoggerFactory.getLogger(EmailHelperServiceImpl.class);

	@Value("${spring.mail.username}") private String username;
	@Value("${spring.mail.password}") private String password;
	@Value("${application.link}") private String url;
	@Value("${spring.mail.port}") private String port;
	@Value("${spring.mail.host}") private String host;
	@Value("${spring.mail.properties.mail.smtp.auth}") private String auth;
	@Value("${spring.mail.properties.mail.smtp.ssl.trust}") private String trust;
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}") private String enable;
	
	private final String HTML_EMAIL = "text/html";
	@Override
	public boolean sendMail(User user) {
		String body="Dear [Name]<br/><br/>Your account has been created on the Analytics Application. Please use the following details to login:<br/><br/>Analytics link: [link]<br/><br/>Username: [username]<br/><br/>Password: [Password]<br/><br/>After logging into your account, please change your password.<br/><br/>Best Regards,<br/><br/>Analytics Admin";
		String from = username;
		String pass = "";
		String to =user.getEmail(); 
		String subject = "Welcome Mail";

		Properties props = System.getProperties();
		//String host = "10.128.15.209";
		props.put("mail.smtp.starttls.enable", enable);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.ssl.trust", trust);

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {

			message.setFrom(new InternetAddress(from));
			InternetAddress toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (AddressException ae) {
			ae.printStackTrace();
		}
		catch (MessagingException me) {
			me.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean sendMailForForgotPassword(String userName,String passwrd,String email) {
		String body="Dear [Name]<br/><br/>Your password to the Analytics application has been reset. Please find below your new password.<br/><br/>Password: [Password]<br/><br/>Best Regards,<br/><br/>Analytics Admin";
		String from = username;
		String pass = "";
		String to =email; 
		String subject = "Analytics Password Reset Notification";

		Properties props = System.getProperties();
		//String host = "10.128.15.209";
		props.put("mail.smtp.starttls.enable", enable);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.ssl.trust", trust);

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {

			message.setFrom(new InternetAddress(from));
			InternetAddress toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			String returnBody=this.mailForForgotPassword(userName,passwrd,body);
			message.setContent(returnBody.trim(),"text/html");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (AddressException ae) {
			ae.printStackTrace();
		}
		catch (MessagingException me) {
			me.printStackTrace();
		}
		return true;
	}


	@Override
	public String mailForForgotPassword(String name,String password,String message) {
		Map<String, String> params=new HashMap<>();
		params.put("name", name);params.put("password", password);
		Map<String, String> keys = regularExpressionconversion(message, params);
		for (Map.Entry<String, String> entry : keys.entrySet()) {
			if (params.containsKey(entry.getValue()))
				message = message.replace(entry.getKey(), params.get(entry.getValue()));
		}
		return message;

	}
	
	private Map<String, String> regularExpressionconversion(String body, Map<String, String> params) {
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(body);
		Map<String, String> keys = new HashMap<>();
		while (m.find()) {
			StringBuffer sb = new StringBuffer();
			boolean isFirst = true;
			for (String s : m.group(1).split(" ")) {
				if (isFirst) {
					isFirst = false;
					sb.append(s.toLowerCase());
				} else {
					String temp = s.toLowerCase();
					if (temp.length() > 0) {
						String first = temp.substring(0, 1);
						String last = temp.substring(1);
						sb.append(first.toUpperCase());
						sb.append(last.toLowerCase());
					}
				}
			}
			keys.put("[" + m.group(1) + "]", sb.toString());
		}
		return keys;
	}


}
