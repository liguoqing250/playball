package org.jeecg.modules.appapi.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.Game;

@Data
@EqualsAndHashCode(callSuper = false)
public class GameBo extends Game {
    private String address;
}
