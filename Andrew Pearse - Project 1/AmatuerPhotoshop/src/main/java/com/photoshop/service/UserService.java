package com.photoshop.service;

import java.util.List;

import com.photoshop.model.User;
import com.photoshop.repo.UserDao;

public class UserService {
	
	private UserDao ud;
	
	public UserService() {
		this(new UserDao());
	}
	
	public UserService(UserDao ud) {
		this.ud = ud;
	}
	
	public boolean verifyUser(String email, String password) {
		return ud.verifyUser(email, password);
	}
	
	public List<User> findAll(){
		return ud.findAll();
	}
	
	public User findById(int i) {
		return ud.findById(i);
	}
	
	public int register(User user) {
		return ud.create(user);
	}
	
	public User findByEmail(String username) {
		return ud.findByEmail(username);
	}
	
	public int updateUserInformation(User user) {
		return ud.update(user);
	}
	
	
}
