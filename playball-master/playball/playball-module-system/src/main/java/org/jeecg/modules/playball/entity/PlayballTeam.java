package org.jeecg.modules.playball.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("playball_team")
public class PlayballTeam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
    private Integer teamId;
    //队名
    private String tName;
    //队徽（图片一张）
    private String tImage;
    //所属单位名
    private String tUnitname;
    //球队类型(关联运动类型表)
    private Integer stId;
    //当前球队人数
    private Integer tPlayersTotal;
    //球队人数上限
    private Integer tPlayersMax;
    //球队介绍 招募说明
    private String tIntroduce;
    //胜 次数
    private Integer tWinTotal;
    //平 次数
    private Integer tDivideTotal;
    //负 次数
    private Integer tLostTotal;
    //球队创建人
    private Integer uId;
    //所属省--对应地区表主键
    private Integer tProvince;
    //所属市--对应地区表主键
    private Integer tCity;
    //所属区--对应地区表主键
    private Integer tDistrict;
    //建队时间
    private Date tCreateTeamTime;
    //队长 球员表ID  p_id
    private Integer tCaptain;
    //纪录创建时间
    private Date createTime;
    //纪录修改时间
    private Date updateTime;
    //逻辑删除
    private Integer isDelete;
    //版本号（用作乐观锁）
    private Integer version;

}