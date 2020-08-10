package org.jeecg.modules.appapi.entity.bo;

import org.jeecg.modules.appapi.entity.PlayballUserCollect;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserCollectBo extends PlayballUserCollect{
	private String title;//标题
	private String resources;//视频 图片
	private String browse;//浏览量
	private String support;//评论量
	private String type;//资源类型 1 图片 2 视频
	
	private Object obj;
	
}
