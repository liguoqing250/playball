package org.jeecg.modules.playball.entity;

import java.util.Date;


import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecgframework.poi.excel.annotation.Excel;
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
@TableName("playball_news")
public class PlayballNews implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    @Excel(name = "资讯标题", width = 15)
    private String title;
    /**
     * 
     */
    @Excel(name = "封面图片", width = 15)
    private String image;

    /**
     * 
     */
    @Excel(name = "资讯内容", width = 15)
    private String content;
    
    /**
     * 运动类型
     */
    private Integer newsType;
    
    @Excel(name = "浏览量", width = 15)
    private Integer pageView;
    
    @Excel(name = "评论量", width = 15)
    private Integer commentTotal;

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
    private Integer delFlg;
    
    /**
     * 
     */
    private Integer version;

    
}
