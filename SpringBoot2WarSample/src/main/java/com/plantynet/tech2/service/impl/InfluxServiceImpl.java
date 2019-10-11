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

import com.plantynet.common.util.lang.DateUtil;
import com.plantynet.tech2.service.InfluxService;
import com.plantynet.tech2.vo.H2oFeet;
import com.plantynet.tech2.vo.H2oFeet2;

//https://github.com/influxdata/influxdb-java 참고
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
		//주의 : 시간은 UTC 기준 또는 epoch 타임으로 넣어도 된다. (DateUtil에 YYYY-MM-DD 넣으면 변환하는 함수 있음)
		//UI input -> UTC 기준 문자열 또는 epoch time (nsec)
		//https://code-examples.net/ko/docs/influxdata/influxdb/v1.3/query_language/data_exploration/index#time-syntax
		
		//SELECT MEAN("water_level") as water_level FROM "h2o_feet" WHERE "location"='coyote_creek' AND time >= 1439856360000000000 AND time <= '2015-08-18T00:54:00Z' GROUP BY time(3m,6m) fill(0)
		//SELECT MEAN(water_level) as mean_level FROM h2o_feet WHERE location='coyote_creek' AND time >= '2015-08-18T00:06:00Z' AND time <= '2015-08-18T00:54:00Z' GROUP BY time(3m,6m) fill(0)
		
		Query query = QueryBuilder.newQuery("SELECT MEAN(water_level) as mean_level FROM h2o_feet WHERE location='coyote_creek' AND time >= $startTime AND time <= $endTime GROUP BY time(3m,6m) fill(0)")
				.forDatabase("NOAA_water_database")
				//.bind("startTime", "2015-08-18T00:06:00Z")
				//.bind("startTime", 1439856360000000000L)//long type
				.bind("startTime", DateUtil.toEpochSecond("2015-08-18 09:06:00", "yyyy-MM-dd HH:mm:ss")*1000_000_000)
				.bind("endTime", "2015-08-18T00:54:00Z")
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
		//mesurement 지정...(범용으로 쓰려면)
		return resultMapper.toPOJO(queryResult, H2oFeet2.class, "h2o_feet");
	}
	
}
