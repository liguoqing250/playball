package org.jeecg.modules.playball.entity;

import java.util.Date;


import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("playball_game_schedule")
public class PlayballGameSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer teamId;
    
    private Integer gamesId;
    
    private Integer stage;
    
    private String groupId;
    
    private Integer opponentId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date matchTime;
    
    private Integer gameStatus;
    
    private Integer gameResult;
    
    private Integer enterBall;
    
    private Integer lostBall;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 
     */
    private Integer isDelete;
    
    /**
     * 
     */
    private Integer version;

    
}
