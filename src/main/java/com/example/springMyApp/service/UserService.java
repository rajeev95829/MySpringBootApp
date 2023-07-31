package com.example.springMyApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.springMyApp.dao.User;
import com.example.springMyApp.exception.ResourceNotFoundException;
import com.example.springMyApp.requestModel.UserModel;


@Component
public interface UserService {
	
	public Optional<User> findByUserId(int userId);
	
	public List<User> findByCity(String city);
	
	public List<User> getAll();
	
	public User saveUser(UserModel user);

	public Optional<User> findByUserEmail(String email);
	
	public void forgetPassword(String email) throws ResourceNotFoundException;
	
	public boolean validateOtp(UserModel user);
	
	public boolean resetPassword(UserModel user);
}
