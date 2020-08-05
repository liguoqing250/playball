package org.jeecg.modules.appapi.entity.bo;

import org.jeecg.modules.appapi.entity.PlayballUserReply;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserReplyBo extends PlayballUserReply{
	private Integer uId; //用户id 
	private String uHeadImage; //用户头像
	private String uNickName; //用户昵称
}
