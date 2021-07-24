package com.coffeegrains.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coffeegrains.auth.entity.Gender;
import com.coffeegrains.auth.repository.users.GenderRepository;

@Component
public class GenderService {

	@Autowired
	private GenderRepository genderRepository;
	public List<Gender> getAllGenders() {
		return genderRepository.getAllGenders();
	}
	
	public boolean genderExists(Integer genderId) {
		if (genderId == null || genderId < 1) {
			return false;
		}
		List<Gender> genders = this.getAllGenders();
		for (Gender gender : genders) {
			if (gender.getId() == genderId) {
				return true;
			}
		}
		return false;
	}
	
	public Gender getGenderById(Integer genderId) {
		if (genderId == null || genderId < 1) {
			return null;
		}
		List<Gender> genders = this.getAllGenders();
		for (Gender gender : genders) {
			if (gender.getId() == genderId) {
				return gender;
			}
		}
		return null;
	}
	
}
