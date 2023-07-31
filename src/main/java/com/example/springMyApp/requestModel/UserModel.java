package com.example.springMyApp.requestModel;

import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	
	private Integer userId;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String passwordBcrypt;
	
	private String gender;
	
	private String phoneNumber;
	
	private String state;
	
	private String city;
	
	private boolean active;
	
	private int otp;
	
	private int createdBy;
	
	private Date createdAt;
		
	private int updatedBy;
	
	private Date updatedAt;
	
	
}
