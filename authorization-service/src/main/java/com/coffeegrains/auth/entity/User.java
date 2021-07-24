/**
 * 
 * @author Dona Ugalde
 * CoffeeGrains Copyright 2021
 * 
 * Entity User to persist a user.
 * 
 */
package com.coffeegrains.auth.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 5382770684716603632L;
	
	private Long id;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private String birthDate;
	private Gender gender;
	private Integer active;
	private Integer verified;
	private String verificationPasscode;
	private String verificationDate;
	private String lastLoginDate;
	private String createdDate;
	private String lastUpdatedDate;
	private String comments;

}
