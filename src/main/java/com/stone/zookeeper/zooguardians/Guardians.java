package com.stone.zookeeper.zooguardians;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 启动类
 * @author stone
 *
 */

@SpringBootApplication
public class Guardians extends SpringBootServletInitializer 
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Guardians.class);
	}
	
	
    public static void main( String[] args )
    {
        SpringApplication.run(Guardians.class, args);
    }
    
    
    @Bean  
    public StringHttpMessageConverter stringHttpMessageConverter(){  
        return new StringHttpMessageConverter();  
    }  
      
    @Bean  
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){  
        return new MappingJackson2HttpMessageConverter();  
    }
}
