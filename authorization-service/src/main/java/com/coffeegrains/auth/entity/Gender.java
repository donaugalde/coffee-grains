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
public class Gender implements Serializable {
	
	private static final long serialVersionUID = -227153509378530714L;
	
	private Integer id;
	private String description;

}
