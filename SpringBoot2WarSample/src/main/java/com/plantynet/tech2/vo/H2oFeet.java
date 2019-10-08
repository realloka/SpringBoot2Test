package com.plantynet.tech2.vo;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Measurement(name = "h2o_feet")
public class H2oFeet
{
	@Column(name = "water_level")
	private Double water_level;
	@Column(name = "level description")
	private String level_description;
	@Column(name = "location")
	private String location;
	@Column(name = "time")
	private Instant time;
}
