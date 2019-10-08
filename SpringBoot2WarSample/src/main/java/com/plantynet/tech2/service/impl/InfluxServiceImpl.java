package com.plantynet.tech2.service.impl;

import java.util.List;

import org.influxdb.dto.BoundParameterQuery.QueryBuilder;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;

import com.plantynet.tech2.service.InfluxService;
import com.plantynet.tech2.vo.H2oFeet;
import com.plantynet.tech2.vo.H2oFeet2;

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
	
	@Override
	public List<H2oFeet2> h2oList2()
	{
		Query query = QueryBuilder.newQuery("SELECT MEAN(water_level) as water_level FROM h2o_feet WHERE location='coyote_creek' AND time >= '2015-08-18T00:06:00Z' AND time <= '2015-08-18T00:54:00Z' GROUP BY time(3m,6m) fill(0)")
				.forDatabase("NOAA_water_database")
				.create();
		
		QueryResult queryResult = influxDBTemplate.query(query);
		
		List<Result> list = queryResult.getResults();
		for(Result item : list)
		{
			List<Series> list2 = item.getSeries();
			
			for(Series item2 : list2)
			{
				System.out.println(item2.getColumns());//List<String>
				System.out.println(item2.getName());
				System.out.println(item2.getTags());//Map<String, String>
				System.out.println(item2.getValues());//List<List<Object>>
			}
		}
		
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper(); // thread-safe - can be reused
		
		return resultMapper.toPOJO(queryResult, H2oFeet2.class);
	}
	
}
