package org.jeecg.modules.appapi.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.PlayballUserReply;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReplyMeData extends PlayballUserReply {
    private String headImage; //用户头像
    private String nickName; //用户昵称
    private Integer type; //数据类型
    private Integer becomId; //所属数据
}
