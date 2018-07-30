package com.iknowhow.springboot.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.iknowhow.springboot.model.User;


@Repository
public class UserRepository {

	    @Autowired
	    JdbcTemplate jdbcTemplate;
	    	    
		public void insertUser (User user) {
	    	String insertQuery = "INSERT INTO user VALUES (?, ?, ?, ?)";
	    	jdbcTemplate.update(insertQuery, user.getId(), user.getName(), user.getAge(), user.getSalary());
	    }
	    
	    public void updateUser (User user) {
	    	String updateQuery = "UPDATE user SET "
	    			+ "name = ?, "
	    			+ "age = ?, "
	    			+ "salary = ? "
	    			+ "WHERE id = ?";
	    	jdbcTemplate.update(updateQuery, user.getName(), user.getAge(), user.getSalary(), user.getId());
	    }
	    
	    public User findById (long id) {
	    	String selectUser = "SELECT * FROM user WHERE id = ?";
	    	User foundUser = (User)jdbcTemplate.queryForObject(selectUser, new Object[] {id}, new BeanPropertyRowMapper(User.class));
	    	return foundUser;
	    }
	    
	    public User findByName (String name) {
	    	String selectUser = "SELECT * FROM user WHERE name = ?";
	    	User foundUser = (User)jdbcTemplate.queryForObject(selectUser, new Object[] {name}, new BeanPropertyRowMapper(User.class));
	    	return foundUser;
	    }
	    
	    public User findUser(User user) {
	    	String selectUser = "SELECT * FROM user WHERE "
	    			+ "name = ? "
	    			+ "AND age = ? "
	    			+ "AND salary = ? ";
	    	Object[] parameters = new Object[] {user.getName(), user.getAge(), user.getSalary()};
	    	User foundUser = (User)jdbcTemplate.queryForObject(selectUser, parameters, new BeanPropertyRowMapper(User.class));
	    	return foundUser;
	    }
	    
	    public List<User> findAllUsers(){
	    	String selectAllUsers = "SELECT * FROM user";
	    	List<User> users = jdbcTemplate.query(selectAllUsers, new BeanPropertyRowMapper(User.class));
	    	return users;
	    }
	    
	    public void deleteUser(long id) {
	    	String deleteUser = "DELETE FROM user WHERE id = ?";
	    	jdbcTemplate.update(deleteUser, id);
	    }
	    
	    public void deleteAllUsers() {
	    	String deleteAllUsers = "TRUNCATE TABLE user";
	    	jdbcTemplate.execute(deleteAllUsers);
	    }
		
}
