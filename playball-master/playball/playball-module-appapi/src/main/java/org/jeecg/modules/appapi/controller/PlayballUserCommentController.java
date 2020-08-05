package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.entity.PlayballUserComment;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCommentBo;
import org.jeecg.modules.appapi.entity.vo.PlayballNewsVo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserCommentVo;
import org.jeecg.modules.appapi.mapper.NewsMapper;
import org.jeecg.modules.appapi.mapper.PlayballUserCommentMapper;
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
 * 用户评论表 前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/UserComment")
public class PlayballUserCommentController {
	
	@Autowired
	private PlayballUserCommentMapper mapper;
	
	@Autowired
	private NewsMapper newMapper;
	
	//获取评论数据(连表查询)
	@GetMapping("/getPlayballUserComment")
	public Result<?> getPlayballUserComment(PlayballUserCommentVo u_comVo){
		//分页
		Page<PlayballUserComment> page = new Page<PlayballUserComment>(u_comVo.getPage(), u_comVo.getLimit());
		//查询条件
		IPage<PlayballUserCommentBo> selectPage = mapper.selectCommentList(page, u_comVo);
		return Result.ok(selectPage);
	}
	
	//用户添加评论
	@PostMapping("/addPlayballUserComment")
	public Result<?> addPlayballUserComment(PlayballUserComment user_com){
		if(user_com.getUcoBecomId()==null){
			return Result.error(0, "评论主键id错误");
		}else if(user_com.getUserId() == null){
			return Result.error(0, "用户id错误");
		}else{
			int insert = mapper.insert(user_com);
			//插入数据成功更新资讯评论数
			if(insert>0){
				PlayballNews news = new PlayballNews();
				news.setId(user_com.getUcoBecomId());
				news.setCommentTotal(1);
				newMapper.updateNewsBrowse(news);
			}
			return Result.ok(user_com);
		}
	}
	
	//更新用户点赞
	@PostMapping("/updateUserSupport")
	public Result<?> updateUserSupport(PlayballUserComment user_com){
		System.err.println("更新用户点赞" +user_com );
		mapper.updateSupport(user_com);
		return Result.ok();
	}

}

