package com.coffeegrains.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coffeegrains.auth.entity.User;
import com.coffeegrains.auth.service.UserService;
import com.coffeegrains.serviceutils.response.ResponseEntityContainer;

@RestController
@RequestMapping(value = "/coffeegrains/authorization-service/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * This endpoint will create a new user that is coming directly from the UI,
	 * in other words, the user did NOT select the option "continue with facebook".
	 */
	@RequestMapping(
			value = "/registerUserUI",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityContainer> registerUserUI(@RequestBody User user) {
		ResponseEntity<ResponseEntityContainer> response = userService.registerUserUI(user);
		return response;
	}

	@RequestMapping(
			value = "/loginWithEmail",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseEntityContainer> loginWithEmail(@RequestBody User user) {
		ResponseEntity<ResponseEntityContainer> response = userService.loginWithEmail(user);
		return response;
	}

}
