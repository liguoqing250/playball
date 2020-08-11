package org.jeecg.modules.appapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballUserDynamic;
import org.jeecg.modules.appapi.entity.bo.PlayballUserDynamicBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserDynamicVo;
import org.jeecg.modules.appapi.mapper.PlayballUserDynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	//查询用户动态发布信息列表
	@GetMapping("/getUserDynamic")
	public Result<?> getUserDynamic(PlayballUserDynamicVo u_dynVo){
		System.err.println("查询用户动态发布信息" + u_dynVo);
		//设置分页数据
		IPage<PlayballUserDynamic> page = new Page<PlayballUserDynamic>(u_dynVo.getPage(), u_dynVo.getLimit());
		
		IPage<PlayballUserDynamicBo> selectPage = mapper.selectDynList(page, u_dynVo);
		return Result.ok(selectPage);
	}
	
	//更新用户点赞、转发
	@PostMapping("/updateFabulous")
	public Result<?> updateFabulous(PlayballUserDynamic u_dyn){
		System.err.println("更新用户点赞、转发" + u_dyn);
		mapper.updateDynamicBrowse(u_dyn);
		return Result.ok();	
	}
	
	//全文搜索查询
	@GetMapping("/findFulltextDynamic")
	public Result<?> findFulltextDynamic(PlayballUserDynamicVo u_dynVo){
		Page<PlayballUserDynamic> page = new Page<PlayballUserDynamic>(u_dynVo.getPage(), u_dynVo.getLimit());
		IPage<PlayballUserDynamicBo> fulltext = mapper.findDynListFulltext(page, u_dynVo.getUdyContent());
		return Result.ok(fulltext);	
	}
	
}

