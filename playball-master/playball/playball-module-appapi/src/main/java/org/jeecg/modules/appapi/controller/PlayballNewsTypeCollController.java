package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.PlayballNewsTypeColl;
import org.jeecg.modules.appapi.mapper.PlayballNewsTypeCollMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 *  用户收藏资讯分类 前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-30
 */
@RestController
@RequestMapping("/NewsTypeColl")
public class PlayballNewsTypeCollController {
	
	@Autowired
	private PlayballNewsTypeCollMapper mapper;
	
	//查询用户收藏的资讯分类数据
	@GetMapping("/getNewsTypeColl")
	public Result<?> getNewsTypeColl(AppUsers user){
		Integer u_id = user.getU_id();
		QueryWrapper<PlayballNewsTypeColl> queryWrapper = new QueryWrapper<PlayballNewsTypeColl>();
		queryWrapper.eq("ntc_user_id", u_id);
		PlayballNewsTypeColl selectOne = mapper.selectOne(queryWrapper);
		return Result.ok(selectOne);
	}

}

