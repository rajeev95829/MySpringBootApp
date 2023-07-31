package com.example.springMyApp.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name= "password_bcrypt")
	private String passwordBcrypt;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="state")
	private String state;
	
	@Column(name="city")
	private String city;
	
	@Column(name="active")
	private boolean active;
	
	@Column(name="otp")
	private int otp;
	
	@Column(name="created_by")
	private int createdBy;
	
	@Column(name="created_at")
	private Date createdAt;
		
	@Column(name="updated_by")
	private int updatedBy;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@PrePersist
	 protected void onCreate() {
		createdAt = new Date();
			
	  }
	
	@PreUpdate
	  protected void onUpdate() {
		updatedAt = new Date();
		
	  }
	
//	@ManyToMany
//	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user", referencedColumnName="user_id"),inverseJoinColumns = @JoinColumn(name="role", referencedColumnName = "role_id"))
//	private Set<Role> roles = new HashSet<>();
}
