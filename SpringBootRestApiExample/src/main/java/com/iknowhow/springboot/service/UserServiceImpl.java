package com.iknowhow.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iknowhow.springboot.model.User;
import com.iknowhow.springboot.repository.UserRepository;



@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findById(long id) {
		return userRepository.findById(id);		
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public void saveUser(User user) {
		List<User> allUsers = findAllUsers();
		int lastId = (allUsers.isEmpty()) ? 0 : allUsers.size();
		user.setId(++lastId);
		userRepository.insertUser(user);	
	}

	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user);		
	}

	@Override
	public void deleteUserById(long id) {
		userRepository.deleteUser(id);
		
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAllUsers();
	}

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAllUsers();
		
	}

	@Override
	public boolean isUserExist(User user) {
		boolean userExists = (userRepository.findUser(user) != null) ? true : false;
		return userExists;
	}



	
}
