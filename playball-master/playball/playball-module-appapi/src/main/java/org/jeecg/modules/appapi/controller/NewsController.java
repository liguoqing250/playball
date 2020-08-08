package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.entity.vo.PlayballNewsVo;
import org.jeecg.modules.appapi.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * <p>
 * 资讯表 前端控制器 APP使用
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-30
 */
@RestController
@RequestMapping("/News")
public class NewsController {
	@Autowired
	private NewsMapper mapper;
	
	//获取资讯信息
	@GetMapping("/getPlayballNews")
	public Result<?> getPlayballNews(PlayballNewsVo newsVo){
		System.err.println(newsVo.getNewsType());
		//分页
		Page<PlayballNews> page = new Page<PlayballNews>(newsVo.getPage(), newsVo.getLimit());
		//查询条件
		QueryWrapper<PlayballNews> queryWrapper = new QueryWrapper<PlayballNews>();
			if(newsVo.getNewsType() != null){
				if(newsVo.getNewsType() != 0){
					queryWrapper.eq("news_type", newsVo.getNewsType());
				}
			}
		queryWrapper.orderByDesc("update_time");
		
		IPage<PlayballNews> selectPage = mapper.selectPage(page, queryWrapper);
		return Result.ok(selectPage);
	}
	
	//更新浏览量
	@PostMapping("/updateBrowse")
	public Result<?> updateBrowse(PlayballNews news){
		System.err.println("更新浏览量" + news);
		int browse = mapper.updateNewsBrowse(news);
		return Result.ok(browse);
	}

}

