package org.jeecg.modules.appapi.entity.bo;

import org.jeecg.modules.appapi.entity.PlayballOrders;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballOrdersBo extends PlayballOrders{
	private String departName;//场馆名称
	private String imageUrl;//场地图片
	private String friTiemRanges;//场地时间
}
