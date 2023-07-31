package com.example.springMyApp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.example.springMyApp.dao.PaginationResponse;
import com.example.springMyApp.dao.User;
import com.example.springMyApp.requestModel.UserModel;
import com.example.springMyApp.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping(value="/getAllUserData", method = RequestMethod.GET)
	public ResponseEntity<PaginationResponse<User>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,HttpServletRequest request
    ) {
        // Fetch all users from the data source
        List<User> allUsers = userService.getAll();

        // Calculate the start and end indices for pagination
        int startIndex = (page - 1) * pageSize;
        if(startIndex>allUsers.size()) {
        	startIndex = 0;
        	page = 1;
        }
        int endIndex = Math.min(startIndex + pageSize, allUsers.size());

        // Extract the paginated users
       
        List<User> paginatedUsers = allUsers.subList(startIndex, endIndex);

        // Create a response object with paginated data and metadata
        PaginationResponse<User> response = new PaginationResponse<>(
                paginatedUsers,
                page,
                pageSize,
                allUsers.size()
        );
        return ResponseEntity.ok(response);
    }
	
	@RequestMapping(value="/findByCity/{city}", method = RequestMethod.GET)
	public List<User> findByCity(@RequestParam String city) {
		List<User> userDataList = userService.findByCity(city);
		return userDataList;
	}
	
	@RequestMapping(value="/getByUserId", method = RequestMethod.GET)
	public Optional<User> getByUserId(@RequestParam int userId) {
		Optional<User> userData = userService.findByUserId(userId);
		return userData;
	}
	
	@RequestMapping(value="/saveUser", method = RequestMethod.POST)
	public User saveUser(@RequestBody UserModel userModel) {
		
		//encoding password with bcryptpasswordencoder
		userModel.setPasswordBcrypt(this.bCryptPasswordEncoder.encode(userModel.getPassword()));
		
		User userData = userService.saveUser(userModel);
		return userData;
	}
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET)
	public ResponseEntity<?> forgotPassword(@RequestParam String email) throws Exception {
		try {
			userService.forgetPassword(email);
			return new ResponseEntity<>("Successfully sent the otp on your email",HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Failed to sent the otp on your email",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/validateOtp", method = RequestMethod.POST)
	public ResponseEntity<?> validateOtp(@RequestBody UserModel userModel) throws Exception {
		boolean isValid=false;
		try {
			 isValid = userService.validateOtp(userModel);
			return new ResponseEntity<>(isValid,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(isValid,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(@RequestBody UserModel userModel) throws Exception {
		boolean isPasswordReset =false;
		try {
			isPasswordReset = userService.resetPassword(userModel);
			return new ResponseEntity<>(isPasswordReset,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(isPasswordReset,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

