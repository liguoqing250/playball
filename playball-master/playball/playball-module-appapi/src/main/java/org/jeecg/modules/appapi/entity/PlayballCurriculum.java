package org.jeecg.modules.appapi.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-08
 * 精选教程实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballCurriculum对象", description="")
public class PlayballCurriculum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "c_id", type = IdType.AUTO)
    private Integer cId;

    @ApiModelProperty(value = "教程标题")
    private String cTitle;

    @ApiModelProperty(value = "教程详情")
    private String cInfo;
    
    @ApiModelProperty(value = "教程视频封面")
    private String cCover;

    @ApiModelProperty(value = "教程视频")
    private String cVideo;

    @ApiModelProperty(value = "运动类型")
    private Integer sId;

    @ApiModelProperty(value = "适龄范围")
    @TableField("c_agerange")
    private String cAgerange;

    @ApiModelProperty(value = "是否付费")
    private byte[] cIsFree;

    @ApiModelProperty(value = "价格")
    private BigDecimal cPrice;

    @ApiModelProperty(value = "点赞数")
    private Integer cFabulous;

    @ApiModelProperty(value = "浏览数（后面根据这个查询热门视频）")
    private Integer cBrowse;
    
    @ApiModelProperty(value = "评论数")
    private Integer cComment;
    
    @ApiModelProperty(value = "用户收藏数")
    private Integer userColls;
    

    @ApiModelProperty(value = "优先级（后期扩展根据级别查询热门）")
    private Integer cFirst;

    @ApiModelProperty(value = "纪录创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "纪录修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    
private Integer isDelete;

    @ApiModelProperty(value = "版本号（用作乐观锁）")
    @Version
    private Integer version;


}
