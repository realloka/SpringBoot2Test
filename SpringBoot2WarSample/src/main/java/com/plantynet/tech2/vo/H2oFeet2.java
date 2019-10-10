package com.plantynet.tech2.vo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.influxdb.annotation.Column;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@Measurement(name = "h2o_feet")
public class H2oFeet2
{
	@Column(name = "water_level")
	private Double water_level;
	@Column(name = "time")
	private Instant time;
	public String getLocalTime() 
	{//epoch 타임 또는 UTC 타임존 기준의 시간이므로 우리나라 시간으로 변경해야 함
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.time.getEpochSecond()), ZoneId.systemDefault());
		
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
