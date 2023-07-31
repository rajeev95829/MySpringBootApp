package com.example.springMyApp.serviceImpl;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springMyApp.dao.User;
import com.example.springMyApp.exception.ResourceNotFoundException;
import com.example.springMyApp.repository.UserRepository;
import com.example.springMyApp.requestModel.UserModel;
import com.example.springMyApp.responseModel.MailRequest;
import com.example.springMyApp.responseModel.MailResponse;
import com.example.springMyApp.service.EmailHelperService;
import com.example.springMyApp.service.EmailService;
import com.example.springMyApp.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	EmailHelperService emailHelperService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Optional<User> findByUserId(int userId) {
		// TODO Auto-generated method stub
		Optional<User> userData = userRepository.findById(userId);
		return userData;
	}

	@Override
	public List<User> findByCity(String city) {
		// TODO Auto-generated method stub
		List<User> userDataList = userRepository.findByCity(city);
		return userDataList;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		List<User> userDataList = userRepository.findAll();
		return userDataList;
	}

	@Override
	public User saveUser(UserModel userModel) {
		// TODO Auto-generated method stub
		User user = mapper.map(userModel, User.class);
		User userData = userRepository.save(user);
		return userData;
	}

	@Override
	public Optional<User> findByUserEmail(String email) {
		// TODO Auto-generated method stub
		Optional<User> userData = userRepository.findByUserEmail(email);
		System.out.println(email);
		return userData;
	}

	@Override
	public void forgetPassword(String email) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userData = userRepository.findByUserEmail(email);
		if (userData.isPresent()) {
				int genOtp = this.generateOTP(6);
				userData.get().setOtp(genOtp);
				User userReturn=userRepository.save(userData.get());
				// emailHelperService.sendMailForForgotPassword(userData.getFirstName(),genPass,userData.getEmail());
				try {
					MailRequest mailRequest=new MailRequest();
					mailRequest.setTo(userData.get().getEmail());
					mailRequest.setName(userData.get().getUserName());
					mailRequest.setSubject("Forgot Password");
					Map<String, Object> mailModel = new HashMap<>();
					mailModel.put("otp", genOtp);
					mailModel.put("username", mailRequest.getName());
					MailResponse mRes = this.sendForgotPasswordMail(mailRequest, mailModel);
					}catch(Exception e) {
						System.out.println("error while sending new usermail");
					}
		} else {
			throw new ResourceNotFoundException("No User Exists");
		}
		
//		private MailResponse sendForgotPasswordMail(MailRequest mailRequest, Map<String, Object> model) {
//			
//			String html=emailService.convertDataToTemplate("forgot-password-mail-template.ftl", model);
//			
//			MailResponse mRes=emailService.sendMail(mailRequest, html);
//			return mRes;
//		}
		
		
	}
	
	private MailResponse sendForgotPasswordMail(MailRequest mailRequest, Map<String, Object> mailModel) {
		// TODO Auto-generated method stub
		String html=emailService.convertDataToTemplate("forgot-password-mail-template.ftl", mailModel);
		
		MailResponse mRes=emailService.sendMail(mailRequest, html);
		return mRes;
	}

	public static int generateOTP(int otpLength) {
        SecureRandom secureRandom = new SecureRandom();
        int max = (int) Math.pow(10, otpLength);
        int otp = secureRandom.nextInt(max);

        // Make sure the OTP has the required number of digits
        while (otp < Math.pow(10, otpLength - 1)) {
            otp *= 10;
        }

        return otp;
    }

	@Override
	public boolean validateOtp(UserModel user) {
		// TODO Auto-generated method stub
		Optional<User> userData = userRepository.findByUserEmail(user.getEmail());
		if(userData.isPresent()) {
			if(user.getOtp()!= 0) {
				if(user.getOtp() == userData.get().getOtp()) {
					return true;
				}
				return false;
			}
			return false;
		}
		
		
		return false;
	}

	@Override
	public boolean resetPassword(UserModel user) {
		// TODO Auto-generated method stub
		User updatedUserData = null;
		Optional<User> userData = userRepository.findByUserEmail(user.getEmail());
		if(userData.isPresent()) {
			if(user.getPassword()!= null) {
				userData.get().setPassword(user.getPassword());
				//encoding password with bcryptpasswordencoder
				userData.get().setPasswordBcrypt(this.bCryptPasswordEncoder.encode(user.getPassword()));
				 updatedUserData = userRepository.save(userData.get());
				 return true;
			}
			else {
				return false;
			}
		}
		
		return false;
	}

}
