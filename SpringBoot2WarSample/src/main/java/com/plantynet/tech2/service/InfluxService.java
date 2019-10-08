package com.plantynet.tech2.service;

import java.util.List;

import com.plantynet.tech2.vo.H2oFeet;
import com.plantynet.tech2.vo.H2oFeet2;

public interface InfluxService
{
	List<H2oFeet> h2oList();
	
	List<H2oFeet2> h2oList2();
}
