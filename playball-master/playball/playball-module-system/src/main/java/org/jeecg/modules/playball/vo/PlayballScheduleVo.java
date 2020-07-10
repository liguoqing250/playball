package org.jeecg.modules.playball.vo;


import java.util.List;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballGames;
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
public class PlayballScheduleVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private PlayballGames gamesInfo;
 
    List<List<PlayballTeam>> matchList;
}
