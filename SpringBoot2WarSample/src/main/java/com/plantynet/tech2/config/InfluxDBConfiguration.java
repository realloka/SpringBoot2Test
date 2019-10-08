package com.plantynet.tech2.config;

import org.influxdb.dto.Point;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.influxdb.DefaultInfluxDBTemplate;
import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBProperties;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.data.influxdb.converter.PointConverter;

@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
//https://github.com/miwurster/spring-data-influxdb
//https://100milliongold.github.io/2019/02/10/spring-boot-%EC%9D%84-influxDB-%EC%99%80-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0/
public class InfluxDBConfiguration
{
	@Bean
	public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties)
	{
		return new InfluxDBConnectionFactory(properties);
	}
	
	@Bean
	public InfluxDBTemplate<Point> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory)
	{
		/*
		 * You can use your own 'PointCollectionConverter' implementation, e.g.
		 * in case you want to use your own custom measurement object.
		 */
		return new InfluxDBTemplate<>(connectionFactory, new PointConverter());
	}
	
	@Bean
	public DefaultInfluxDBTemplate defaultTemplate(final InfluxDBConnectionFactory connectionFactory)
	{
		/*
		 * If you are just dealing with Point objects from 'influxdb-java' you
		 * could also use an instance of class DefaultInfluxDBTemplate.
		 */
		return new DefaultInfluxDBTemplate(connectionFactory);
	}
}