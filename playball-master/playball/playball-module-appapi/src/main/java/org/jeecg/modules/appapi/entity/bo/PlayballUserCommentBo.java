package org.jeecg.modules.appapi.entity.bo;

import org.jeecg.modules.appapi.entity.PlayballUserComment;

import lombok.Data;
import lombok.EqualsAndHashCode;
//用户评论连表查询结果集

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserCommentBo extends PlayballUserComment{
	private Integer uId; //用户id 
	 private String uHeadImage; //用户头像
	 private String uNickName; //用户昵称
}
