package com.iknowhow.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.iknowhow.springboot.model.User;
import com.iknowhow.springboot.service.UserService;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> allUsers = userService.findAllUsers();
		logger.info("Retrieved all users");
		return ResponseEntity.ok().body(allUsers);
	}

	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {		
		User foundUser = userService.findById(id);
		logger.info("Retrieved single user. ID: " + id);
		return ResponseEntity.ok().body(foundUser);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@Valid @RequestBody User user, UriComponentsBuilder ucBuilder) {
		userService.saveUser(user);
		UriComponents uriComponents = ucBuilder.scheme("http").host("com.iknowhow.springboot").path("/api/user/{id}").buildAndExpand(user.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComponents.toUri());
		logger.info("Created new user. Location: " + uriComponents.toUri());
		return ResponseEntity.created(uriComponents.toUri()).headers(headers).body(user);
	}

	// ------------------- Update a User ------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
		user.setId(id);
		userService.updateUser(user);
		logger.info("Updated user. ID: " + id);
		return ResponseEntity.ok().body(user);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		userService.findById(id); // added this line to prevent continuation of the code if there is no user with that ID
		userService.deleteUserById(id);
		logger.info("Deleted user. ID: " + id);
		return ResponseEntity.ok().body("User deleted!");
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		userService.deleteAllUsers();
		logger.info("Deleted ALL users!");
		return ResponseEntity.ok().body(new User());
	}

}