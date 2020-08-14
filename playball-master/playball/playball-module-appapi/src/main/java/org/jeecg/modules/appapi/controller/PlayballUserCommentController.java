package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballCurriculum;
import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.entity.PlayballUserComment;
import org.jeecg.modules.appapi.entity.PlayballUserDynamic;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCommentBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserCommentVo;
import org.jeecg.modules.appapi.entity.vo.UserCommentVo;
import org.jeecg.modules.appapi.mapper.NewsMapper;
import org.jeecg.modules.appapi.mapper.PlayballCurriculumMapper;
import org.jeecg.modules.appapi.mapper.PlayballUserCommentMapper;
import org.jeecg.modules.appapi.mapper.PlayballUserDynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	private NewsMapper newMapper;//资讯 mapper
	
	@Autowired
	private PlayballCurriculumMapper curMapper;// 教程 mapper
	
	@Autowired
	private PlayballUserDynamicMapper dynMapper; //动态 mapper
	
	//获取评论数据(连表查询)
	@GetMapping("/selectDynamicByCommentUid")
	public Result<?> selectDynamicByCommentUid(PlayballUserCommentVo u_comVo){
		//分页
		Page<PlayballUserComment> page = new Page<PlayballUserComment>(u_comVo.getPage(), u_comVo.getLimit());
		//查询条件
		IPage<UserCommentVo> selectPage = mapper.selectDynamicByCommentUid(page, u_comVo.getUserId());
		return Result.ok(selectPage);
	}
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
				updateComment(user_com.getUcoBecomId(),user_com.getUcoSort());
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
	
	//更新评论数
	public void updateComment(int id,Integer type){
		if(type == null){
			return ;
		}else if(type == 1){
			PlayballNews news = new PlayballNews();
			news.setId(id);//id
			news.setCommentTotal(1);//评论量字段不为空
			newMapper.updateNewsBrowse(news);
		}else if(type == 2){
			PlayballCurriculum curriculum = new PlayballCurriculum();
			curriculum.setCId(id);
			curriculum.setCComment(1);
			curMapper.updateCurriculumBrowse(curriculum);
		}else if(type == 3){
			PlayballUserDynamic dynamic = new PlayballUserDynamic();
			dynamic.setUdyId(id);//id
			dynamic.setUdyComment(1);//评论量字段不为空
			dynMapper.updateDynamicBrowse(dynamic);
		}
	}

}

