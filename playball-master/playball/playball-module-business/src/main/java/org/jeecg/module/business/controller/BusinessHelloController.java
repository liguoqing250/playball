package org.jeecg.module.business.controller;

/*import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags="新建module--jm")
@RestController
@RequestMapping("/hello")
public class BusinessHelloController  {
	@GetMapping(value="/")
	public Result<String> hello(){
		Result<String> result = new Result<String>();
		result.setResult("hello word!");
		result.setSuccess(true);
		return result;
	}
}*/

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/business")
@Slf4j
public class BusinessHelloController {

	@GetMapping(value = "/hello")
	public Result<String> hello() {
		Result<String> result = new Result<String>();
		result.setResult("Hello World!");
		result.setSuccess(true);
		return result;
	}
}