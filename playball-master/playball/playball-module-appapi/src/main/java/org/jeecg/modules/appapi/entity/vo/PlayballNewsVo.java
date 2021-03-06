package org.jeecg.modules.appapi.entity.vo;

import java.util.List;

import org.jeecg.modules.appapi.entity.PlayballNews;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballNewsVo extends PlayballNews{
	private Integer page = 1;
	private Integer limit = 5;
	
	//接收多个 id 数组批量删除
	private List<Integer> ids;
}
