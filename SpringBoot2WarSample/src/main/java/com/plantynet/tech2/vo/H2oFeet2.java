package com.plantynet.tech2.vo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Measurement(name = "h2o_feet")
public class H2oFeet2
{
	@Column(name = "water_level")
	private Double water_level;
	@Column(name = "time")
	private Instant time;
	public String getLocalTime() 
	{
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.time.getEpochSecond()), ZoneId.systemDefault());
		
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
