package com.coffeegrains.auth.repository.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.coffeegrains.auth.config.CacheConfig;
import com.coffeegrains.auth.entity.Gender;

@Repository
public class GenderRepository {
	
	@Autowired
	@Qualifier(value = "authJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

	@Cacheable(CacheConfig.ALL_GENDERS)
	public List<Gender> getAllGenders() {
		
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		System.out.println("We're going to genders DB table...");
		
		List<Gender> genders = new ArrayList<>();
		StringBuilder query = new StringBuilder().append("select id, description from genders order by id asc");
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query.toString());
		for (Map<String, Object> result : resultList) {	
			Gender gender = new Gender();
			gender.setId(Integer.valueOf(String.valueOf(result.get("id"))));
			gender.setDescription(String.valueOf(result.get("description")));
			genders.add(gender);
		}
		return genders;
	}
	
}
