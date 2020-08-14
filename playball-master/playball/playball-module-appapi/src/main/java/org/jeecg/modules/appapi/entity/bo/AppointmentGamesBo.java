package org.jeecg.modules.appapi.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.AppointmentGames;


@Data
@EqualsAndHashCode(callSuper = false)

public class AppointmentGamesBo extends AppointmentGames {
    private Integer uId; //用户id
    private String headImage; //用户头像
    private String nickName; //用户昵称
    private String name; //用户昵称
}
