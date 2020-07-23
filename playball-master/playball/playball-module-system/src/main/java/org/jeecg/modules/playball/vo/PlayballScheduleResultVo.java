package org.jeecg.modules.playball.vo;


import java.util.List;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballSchedule;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @Author 
 * @since 
 */
@Data
public class PlayballScheduleResultVo {
		
	private PlayballSchedule  gameSchedule;
    
	private PlayballTeam team;
    //对手
	private PlayballTeam topponent;
}
