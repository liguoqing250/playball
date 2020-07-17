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
public class PlayballEnrollPage {
	
    private Integer id;
    
    private Integer teamId;//球队id
    
    private Integer gamesId;//赛事id
    
    private Date createTime;//报名时间
    
    private String gamesName; //赛事名

    private String teamName; //球队名
    
    private String imageUrl; //球队图片链接
    
}
