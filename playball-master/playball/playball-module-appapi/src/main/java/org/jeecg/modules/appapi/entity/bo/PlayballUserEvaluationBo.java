package org.jeecg.modules.appapi.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.BusinessEvaluation;
import org.jeecg.modules.appapi.entity.PlayballUserReply;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlayballUserEvaluationBo extends BusinessEvaluation {
	private Integer uId; //用户id 
	private String uHeadImage; //用户头像
	private String uNickName; //用户昵称
}
