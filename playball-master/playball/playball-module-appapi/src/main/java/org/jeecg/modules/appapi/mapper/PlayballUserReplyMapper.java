package org.jeecg.modules.appapi.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballUserReply;
import org.jeecg.modules.appapi.entity.bo.PlayballUserReplyBo;
import org.jeecg.modules.appapi.entity.bo.ReplyMeData;
import org.jeecg.modules.appapi.entity.vo.PlayballUserReplyVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 用户回复表 Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-03
 */
public interface PlayballUserReplyMapper extends BaseMapper<PlayballUserReply> {
	IPage<PlayballUserReplyBo> selectReplyList(IPage<PlayballUserReply> page,@Param("vo") PlayballUserReplyVo vo);

    IPage<ReplyMeData> getReplyMeData(Page<PlayballUserReply> page, @Param("vo") PlayballUserReplyVo u_replyVo);
}
