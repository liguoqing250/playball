package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.entity.PlayballUserComment;
import org.jeecg.modules.appapi.entity.PlayballUserReply;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCommentBo;
import org.jeecg.modules.appapi.entity.bo.PlayballUserReplyBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserReplyVo;
import org.jeecg.modules.appapi.mapper.PlayballUserCommentMapper;
import org.jeecg.modules.appapi.mapper.PlayballUserReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 用户回复表 前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/UserReply")
public class PlayballUserReplyController {

	@Autowired
	private PlayballUserReplyMapper mapper;
	
	//评论 mapper
	@Autowired
	private PlayballUserCommentMapper comMapper;
	
	//获取回复数据(连表查询)
	@GetMapping("/getPlayballUserReply")
	public Result<?> getPlayballUserComment(PlayballUserReplyVo u_replyVo){
		//分页
		Page<PlayballUserReply> page = new Page<PlayballUserReply>(u_replyVo.getPage(), u_replyVo.getLimit());
		//查询条件
		IPage<PlayballUserReplyBo> selectPage = mapper.selectReplyList(page, u_replyVo);
		return Result.ok(selectPage);
	}
	
	//用户添加回复
	@PostMapping("/addPlayballUserReply")
	public Result<?> addPlayballUserReply(PlayballUserReply reply){
		if(reply.getUreUcoid()==null){
			return Result.error(0, "评论主键id错误");
		}else if(reply.getUserId() == null){
			return Result.error(0, "用户id错误");
		}else{
			int insert = mapper.insert(reply);
			//插入数据成功更新资讯评论数
			if(insert>0){
				//更新评论回复数
				PlayballUserComment comment = new PlayballUserComment();
				comment.setUcoId(reply.getUreUcoid());
				comment.setUcoReplyNum(1);
				comMapper.updateSupport(comment);
				return Result.ok(reply);
			}
			return Result.error(0, "插入失败");
		}
	}
}

