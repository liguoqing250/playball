package org.jeecg.modules.appapi.controller;


import java.util.List;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballCurriculum;
import org.jeecg.modules.appapi.entity.PlayballSportsType;
import org.jeecg.modules.appapi.entity.vo.PlayballCurriculumVo;
import org.jeecg.modules.appapi.mapper.PlayballCurriculumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-08
 * 精选教程控制层
 */
@RestController
@RequestMapping("/playballCurriculum")
public class PlayballCurriculumController {
	@Autowired
	private PlayballCurriculumMapper mapper;
	
	/* APP 调用查询视频列表 by 分类*/
	@GetMapping("/getPlayballCurriculum")
	public Result<JSONObject> getPlayballCurriculum(PlayballCurriculumVo curVo){
		System.err.println("查询教程资源列表");
		System.err.println(curVo);
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		//设置查询条件
		QueryWrapper<PlayballCurriculum> queryWrapper = new QueryWrapper<PlayballCurriculum>();
		if(curVo.getSId()!=0){
			queryWrapper.eq("s_id", curVo.getSId());
		}
		queryWrapper.orderByDesc("update_time");
		//设置分页
		Page<PlayballCurriculum> page = new Page<PlayballCurriculum>(curVo.getPage(),curVo.getLimit());
		IPage<PlayballCurriculum> selectPage = mapper.selectPage(page, queryWrapper);
		obj.put("data", selectPage.getRecords());
		obj.put("pages", selectPage.getPages());
		result.setCode(200);
		result.setResult(obj);
		return result;
	}
	//根据id查询教程数据
	@GetMapping("/findPlayballCurriculum")
	public Result<?> findPlayballCurriculum(PlayballCurriculum cur){
		return Result.ok(mapper.selectById(cur.getCId()));
	}
	
	/* APP 调用查询热门视频*/
	@GetMapping("/getHotCurriculum")
	public Result<JSONObject> getHotCurriculum(PlayballCurriculumVo curVo){
		System.err.println("查询热门视频");
		//返回对象
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		//设置查询条件
		QueryWrapper<PlayballCurriculum> queryWrapper = new QueryWrapper<PlayballCurriculum>();
		queryWrapper.orderByDesc("c_browse");
		//设置分页
		Page<PlayballCurriculum> page = new Page<PlayballCurriculum>(curVo.getPage(),curVo.getLimit());
		IPage<PlayballCurriculum> selectPage = mapper.selectPage(page, queryWrapper);
		obj.put("data", selectPage.getRecords());
		result.setCode(200);
		result.setResult(obj);
		return result;
	}
	
	/* APP 调用查询热门视频*/
	@PostMapping("/updateHotCurriculum")
	public Result<JSONObject> updateHotCurriculum(@RequestBody PlayballCurriculum cur){
		System.err.println("更新浏览量" + cur);
		//返回对象
		Result<JSONObject> result = new Result<JSONObject>();
		//设置分页
		int update = mapper.updateHots(cur.getCId());
		if(update>0){
			result.setCode(200);
		}else{
			result.setCode(0);
		}
		return result;
	}

}

