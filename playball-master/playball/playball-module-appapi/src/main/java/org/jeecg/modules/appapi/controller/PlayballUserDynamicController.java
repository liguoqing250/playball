package org.jeecg.modules.appapi.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballUserDynamic;
import org.jeecg.modules.appapi.entity.PlayballUsersBalance;
import org.jeecg.modules.appapi.mapper.PlayballUserDynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 *  用户发布动态控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/UserDynamic")
public class PlayballUserDynamicController {

	@Autowired
	private PlayballUserDynamicMapper mapper;
	
	//用户添加动态发布信息
	@PostMapping("/insertUserDynamic")
	public Result<?> insertUserDynamic(PlayballUserDynamic u_dyn){
		System.err.println("添加动态发布信息" + u_dyn);
		int insert = mapper.insert(u_dyn);
		if(insert>0){
			return Result.ok("添加成功");
		}
		return Result.error(0, "添加成功");
	}
}

