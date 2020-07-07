package org.jeecg.modules.appapi.controller;

import java.util.List;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.Demo;
import org.jeecg.modules.appapi.mapper.DemoMapper;
import org.jeecg.modules.appapi.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestRedisController {
	@Autowired
	private RedisServiceImpl redis;
	
	@Autowired
	private DemoMapper mapper;

	@PostMapping("/test")
	public Result<JSONObject> test(@RequestBody Demo d){
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		redis.set("liuerzxc", "iaou", 600L);//测试 Redis 
		System.err.println(d);
		int insert = mapper.insert(d);
		mapper.select
		if(insert>0){
			return result.success("操作成功！！！");
		}
			return result.success("操作失败！！！");
	}
}
