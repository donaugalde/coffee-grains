package com.coffeegrains.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

	public static final String ALL_GENDERS = "cacheAllGenders";
	
	@Bean
	public CacheManager cacheManagerAllGenders() {
		return new ConcurrentMapCacheManager(CacheConfig.ALL_GENDERS);
	}

	@Override
	public void customize(ConcurrentMapCacheManager cacheManager) {
		List<String> cacheNames = new ArrayList<>();
		
		// We must add all cache names to this ArrayList
		cacheNames.add(CacheConfig.ALL_GENDERS);
		
		cacheManager.setCacheNames(cacheNames);
	}

}
