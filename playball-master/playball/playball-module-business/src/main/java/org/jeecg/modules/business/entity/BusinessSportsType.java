package org.jeecg.modules.business.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 运动类型
 * @Author: 
 * @Date:  2019-02-15
 * @Version: V1.0
 */
@Data
@TableName("playball_sports_type")
public class BusinessSportsType implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
    @TableId(type = IdType.ID_WORKER_STR)
	private java.lang.Integer id;
	/**运动类型*/
	private java.lang.String sportsName;

}
