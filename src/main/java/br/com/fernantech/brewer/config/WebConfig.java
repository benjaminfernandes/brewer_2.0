package br.com.fernantech.brewer.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableCaching
@EnableAsync
public class WebConfig implements WebMvcConfigurer {
	
	@Bean
	public CacheManager cacheManager() throws Exception {

		return new ConcurrentMapCacheManager();
	}
}