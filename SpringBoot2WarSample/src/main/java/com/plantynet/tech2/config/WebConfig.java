package com.plantynet.tech2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	@Value("${custom.static.location}")
    private String staticLocation;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		//반드시 base 다음에/ 을주어야한다.
		registry.addResourceHandler("/base/**").addResourceLocations(staticLocation + "/base/");//.setCachePeriod(20);//20초
		registry.addResourceHandler("/images/**").addResourceLocations(staticLocation + "/images/");//.setCachePeriod(20);
	}
	
}
