package org.jeecg.modules.appapi.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballUserComment;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCommentBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserCommentVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.appapi.entity.vo.UserCommentVo;

/**
 * <p>
 * 用户评论表 Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-03
 */
public interface PlayballUserCommentMapper extends BaseMapper<PlayballUserComment> {
	IPage<PlayballUserCommentBo> selectCommentList(IPage<PlayballUserComment> page,@Param("vo") PlayballUserCommentVo vo);
	
	//更新点赞数/回复数
	int updateSupport(PlayballUserComment p_user);

    IPage<UserCommentVo> selectDynamicByCommentUid(Page<PlayballUserComment> page, @Param("uId")Integer userId);
}
