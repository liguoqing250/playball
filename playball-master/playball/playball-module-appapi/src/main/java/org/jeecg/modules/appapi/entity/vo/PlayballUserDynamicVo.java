package org.jeecg.modules.appapi.entity.vo;

import java.util.List;

import org.jeecg.modules.appapi.entity.PlayballUserDynamic;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserDynamicVo extends PlayballUserDynamic{
	private Integer page = 1; //起始页
	private Integer limit = 5; //每页显示
	private Integer uId;	//用户ID
	//接收多个 id 数组批量删除
	private List<Integer> ids;
}
