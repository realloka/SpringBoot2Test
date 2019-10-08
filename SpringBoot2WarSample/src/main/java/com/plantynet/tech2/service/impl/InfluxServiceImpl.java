package com.plantynet.tech2.service.impl;

import java.util.List;

import org.influxdb.dto.BoundParameterQuery.QueryBuilder;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import com.plantynet.tech2.service.InfluxService;
import com.plantynet.tech2.vo.H2oFeet;

@Service
public class InfluxServiceImpl implements InfluxService
{
	//@Autowired -> 생성자 주입
    private InfluxDBTemplate<Point> influxDBTemplate;
    
    //@Autowired -> spring 4.3+ 생성자 하나인 경우 자동으로 설정됨
    public InfluxServiceImpl(InfluxDBTemplate<Point> influxDBTemplate)
    {
        this.influxDBTemplate = influxDBTemplate;
    }
	
	@Override
	public List<H2oFeet> h2oList()
	{
		Query query = QueryBuilder.newQuery("SELECT * FROM h2o_feet LIMIT 1000")
		        .forDatabase("NOAA_water_database")
		        .create();

		QueryResult queryResult = influxDBTemplate.query(query);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused

		return resultMapper.toPOJO(queryResult, H2oFeet.class);
	}
	
}
