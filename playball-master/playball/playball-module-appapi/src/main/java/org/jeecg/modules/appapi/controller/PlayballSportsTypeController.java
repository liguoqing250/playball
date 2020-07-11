package org.jeecg.modules.appapi.controller;


import java.util.List;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.Demo;
import org.jeecg.modules.appapi.entity.PlayballSportsType;
import org.jeecg.modules.appapi.mapper.PlayballSportsTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @since 2020-07-08
 * 运动类型控制器
 */
@RestController
@RequestMapping("/playballSportsType")
public class PlayballSportsTypeController {
	
	@Autowired
	private PlayballSportsTypeMapper mapper;
	
	/* APP 调用*/
	@GetMapping("/getPlayballSportsType")
	public Result<JSONObject> getPlayballSportsType(){
		System.err.println("查询运动分类");
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		List<PlayballSportsType> list = mapper.selectList(null);
		obj.put("data", list);
		result.setCode(200);
		result.setResult(obj);
		return result;
	}
}

