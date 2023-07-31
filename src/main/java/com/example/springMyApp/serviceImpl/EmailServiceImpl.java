package com.example.springMyApp.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.example.springMyApp.responseModel.MailRequest;
import com.example.springMyApp.responseModel.MailResponse;
import com.example.springMyApp.service.EmailService;
import com.example.springMyApp.service.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService{

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	@Autowired
	private UserService usersService;

	@Value("${spring.mail.username}") private String username;
	@Value("${spring.mail.password}") private String password;

	@Override
	public MailResponse sendMail(MailRequest request, String html) {

		MailResponse response=new MailResponse();
		MimeMessage message=sender.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());

			helper.setTo(request.getTo());		
			helper.setFrom(username);
			helper.setSubject(request.getSubject());
			helper.setText(html, true);
			sender.send(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String convertDataToTemplate(String templateName,Map<String, Object> model) {
		String html="";
		try {

			Template t=config.getTemplate(templateName);

			html=FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		}catch(Exception e) {

		}
		return html;
	}
}
