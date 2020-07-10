package org.jeecg.modules.playball.vo;


import java.util.List;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.entity.PlayballGameSchedule;
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
public class PlayballScheduleResultVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PlayballGameSchedule  gameSchedule;
    
	private PlayballTeam team;
    //对手
	private PlayballTeam topponent;
}
