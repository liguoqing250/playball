package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.mapper.PlayballNewsTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资讯分类 前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-30
 */
@RestController
@RequestMapping("/NewsType")
public class PlayballNewsTypeController {
	@Autowired
	private PlayballNewsTypeMapper mapper;
	
	//查询用户收藏的资讯分类数据
	@GetMapping("/getNewsType")
	public Result<?> getNewsType(){
		return Result.ok(mapper.selectList(null));
	}

}

