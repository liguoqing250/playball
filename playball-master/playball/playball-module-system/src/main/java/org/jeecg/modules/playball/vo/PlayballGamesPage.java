package org.jeecg.modules.playball.vo;

import java.util.Date;

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
public class PlayballGamesPage {
	
    private Integer id;
    
    private Integer stage;
    
    private String gamesName;

    private String gamesInfo;
    
    private String gameRule;
    
    private String gameImage;
    
    private String gameType;
    
    private Date enrollTime;
    
    private Date startTime;
    
    private Date endTime;

    private Integer sportsId;
    
    private Integer fieldId;
    
    private String sportsName;
    
    private String fieldName;
    
    private String businessName;
    
    private Integer isDelete;
    
}
