package org.jeecg.modules.playball.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.playball.entity.PlayballGames;
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
public class PlayballGamesPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    
    private Integer stage;
    
    private String gamesName;

    private String gamesInfo;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enrollTime;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer sportsId;
    
    private Integer fieldId;
    
    private String sportsName;
    
    private String fieldName;
    
    private String businessName;
    
    private Integer isDelete;
    
}
