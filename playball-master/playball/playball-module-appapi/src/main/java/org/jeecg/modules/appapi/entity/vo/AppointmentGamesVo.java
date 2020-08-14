package org.jeecg.modules.appapi.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.AppointmentGames;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppointmentGamesVo extends AppointmentGames {
    private Integer page = 1; //起始页
    private Integer limit = 5; //每页显示
    private Integer uId;

}
