package org.jeecg.modules.appapi.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.Game;

@Data
@EqualsAndHashCode(callSuper = false)
public class GameVo extends Game {
    private Integer page = 1; //起始页
    private Integer limit = 5; //每页显示
    private Integer uId;
}
