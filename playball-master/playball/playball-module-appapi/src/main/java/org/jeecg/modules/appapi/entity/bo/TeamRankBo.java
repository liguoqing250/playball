package org.jeecg.modules.appapi.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.modules.appapi.entity.ScheduleUserdata;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamRankBo extends ScheduleUserdata {
    private Integer teamId;
    private String tName;
    private String tImage;

}
