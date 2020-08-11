package org.jeecg.modules.appapi.entity.vo;

import java.util.List;

import org.jeecg.modules.appapi.entity.PlayballUserCollect;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserCollectVo extends PlayballUserCollect{
	private Integer page = 1;
	private Integer limit = 6;
	
	private String lat ="";
	private String lon ="";
	
	//接收多个 id 数组批量删除
	private List<Integer> ids;
}
