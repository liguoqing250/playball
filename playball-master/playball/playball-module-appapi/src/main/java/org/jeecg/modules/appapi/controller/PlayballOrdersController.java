package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballOrders;
import org.jeecg.modules.appapi.mapper.PlayballOrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-15
 */
@RestController
@RequestMapping("/playballOrders")
public class PlayballOrdersController {

	@Autowired
	private PlayballOrdersMapper mapper;
	
	/* APP 调用添加订单数据*/
	@PostMapping("/addPlayballOrders")
	public Result<JSONObject> addPlayballOrders(@RequestBody PlayballOrders ord){
		//返回对象
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		int insert = mapper.insert(ord);
		System.err.println("添加订单数据" + ord);
		obj.put("data", ord);
		if(insert>0){
			result.setCode(200);
			result.setResult(obj);
		}else{
			result.setCode(0);
		}
		return result;
	}
}

