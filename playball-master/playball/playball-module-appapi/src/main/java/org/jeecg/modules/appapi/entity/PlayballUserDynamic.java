package org.jeecg.modules.appapi.entity;

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
 * @since 2020-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballUserDynamic对象", description="")
public class PlayballUserDynamic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户动态表主键id")
    @TableId(value = "udy_id", type = IdType.AUTO)
    private Integer udyId;

    @ApiModelProperty(value = "用户id")
    private Integer udyUserId;

    @ApiModelProperty(value = "发布动态内容")
    private String udyContent;

    @ApiModelProperty(value = "发布动态图片（最大支持 9 张图片）发布动态视频（最大支持 1 个视频）")
    private String udyResources;

    @ApiModelProperty(value = "动态资源类型：1 图片 2视频")
    private Integer udyResType;

    @ApiModelProperty(value = "发布动态信息位置（系统自动获取）")
    private String udyPosition;

    @ApiModelProperty(value = "是否可见1、全部人2、仅自己")
    private Integer udyVisible;

    @ApiModelProperty(value = "预编辑 1、已完成 2、草稿")
    private Integer udyDraft;
    
    @ApiModelProperty(value = "用户点赞数")
    private Integer udySupport;
    
    @ApiModelProperty(value = "用户评论数")
    private Integer udyComment;

    @ApiModelProperty(value = "用户转发数")
    private Integer udyShare;
    
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
