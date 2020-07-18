package org.jeecg.modules.playball.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @Author 
 * @since 
 */
@Data
public class PlayballScheduleInfoPage {
	//赛程id主建
    private Integer id;
    //赛事阶段用来做循环
    private Integer stage;
    //赛事id
    private Integer gamesId;
    //球队id
    private Integer teamId;
    //对手id
    private Integer opponentId;
    //分组id
    private Integer groupId;
    //比赛结果
    private Integer gameStatus;
    //比赛状态
    private Integer gameResult;
    //进球数
    private Integer enterBall;
    //失球数
    private Integer lostBall;
    //赛事名称
    private String gamesName;
    //球队姓名
    private String teamName;
    //对手球队姓名
    private String opponentName;
    //比赛时间
    private Date matchTime;
    
}
