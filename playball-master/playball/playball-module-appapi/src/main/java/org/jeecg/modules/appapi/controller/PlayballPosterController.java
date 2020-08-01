package org.jeecg.modules.appapi.controller;


import java.util.List;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballPoster;
import org.jeecg.modules.appapi.mapper.PlayballPosterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 广告表 前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-01
 */
@RestController
@RequestMapping("/Poster")
public class PlayballPosterController {
	
	@Autowired
	private PlayballPosterMapper mapper;
	
	//查询所有广告数据
	@GetMapping("/getPlayballPoster")
	public Result<?> getNewsTypeColl(){
		List<PlayballPoster> selectList = mapper.selectList(null);
		return Result.ok(selectList);
	}

}

