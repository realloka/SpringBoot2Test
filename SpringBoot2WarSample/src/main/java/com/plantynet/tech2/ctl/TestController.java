package com.plantynet.tech2.ctl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.plantynet.common.base.ctl.BaseController;
import com.plantynet.common.base.vo.ColumnCriterias;
import com.plantynet.common.base.vo.DataTableOutput;
import com.plantynet.common.base.vo.OrderCriterias;
import com.plantynet.tech2.service.TestService;
import com.plantynet.tech2.vo.TestVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TEST")
@Controller
//@RequestMapping(value = "/test1")
public class TestController extends BaseController
{
	// @Autowired -> 생성자 주입
	TestService testService;
	
	// @Autowired -> spring 4.3+ 생성자 하나인 경우 자동으로 설정됨
	public TestController(TestService testService)
	{
		this.testService = testService;
	}
	
	@GetMapping({ "/test1", "test1/index" })
	public String templateMain(String param, Model model)
	{
		log.info("{}", param);
		
		model.addAttribute("content", "test1/index");
		
		//추가 jar 없이 기본적인 방법으로... 직관적이지는 않다.
		
		return "test1/layout/defaultLayout";
	}
	
	@GetMapping({ "/test2", "test2/index" })
	public String templateMain2(String param, Model model)
	{
		log.info("{}", param);
		
		//추가 jar 필요 : nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect
		
		return "test2/index";
	}
	
	@GetMapping("/test2/list")
	public String templateList(@ModelAttribute("search")TestVo testVo, Model model)
	{
		testVo.setLength(5);
		
		//단일 정렬인 경우
        List<Map<OrderCriterias, String>> orderList = testVo.getOrder();
        for(Map<OrderCriterias, String> item : orderList)
        {
            int index = Integer.parseInt(item.get(OrderCriterias.column));
            String dir = item.get(OrderCriterias.dir);
            
            testVo.setSortColumn(testVo.getColumns().get(index).get(ColumnCriterias.name));
            testVo.setSortOrder(dir.toUpperCase());
        }
        
        DataTableOutput<TestVo> dataTable = testService.selectTestList(testVo);
        
        model.addAttribute("list", dataTable.getData());
        model.addAttribute("totalCnt", dataTable.getRecordsTotal());
		
		return "test2/list";
	}
}