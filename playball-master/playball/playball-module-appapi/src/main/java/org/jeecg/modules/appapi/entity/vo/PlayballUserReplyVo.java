package org.jeecg.modules.appapi.entity.vo;

import java.util.List;

import org.jeecg.modules.appapi.entity.PlayballUserReply;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserReplyVo extends PlayballUserReply{
	private Integer page = 1; //起始页
	private Integer limit = 5; //每页显示
	private Integer uId;
	//接收多个 id 数组批量删除
	private List<Integer> ids;
}
