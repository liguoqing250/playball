package org.jeecg.modules.appapi.entity.bo;

import java.util.List;

import org.jeecg.modules.appapi.entity.PlayballUserDynamic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserDynamicBo extends PlayballUserDynamic{

	 private Integer uId; //用户id 
	 private String uHeadImage; //用户头像
	 private String uNickName; //用户昵称
}
