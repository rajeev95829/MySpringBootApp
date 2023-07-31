package com.example.springMyApp.serviceImpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springMyApp.dao.User;
import com.example.springMyApp.service.UserService;

@Service
public class JwtUserDetailService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(username);
		Optional<User> user = userService.findByUserEmail(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		if (!user.get().isActive()) {
			throw new UsernameNotFoundException("User is Inactive");
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPasswordBcrypt(),
				new ArrayList<>());
	}

}
