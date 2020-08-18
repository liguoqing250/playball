package org.jeecg.modules.appapi.entity.bo;

import org.jeecg.modules.appapi.entity.Arena;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ArenaBo extends Arena{
	private String price;//价格
	private String distance;//距离
}
