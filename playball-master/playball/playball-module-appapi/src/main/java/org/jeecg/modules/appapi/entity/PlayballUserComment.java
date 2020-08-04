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
 * 用户评论表
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballUserComment对象", description="用户评论表")
public class PlayballUserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户评论表主键id")
    @TableId(value = "uco_id", type = IdType.AUTO)
    private Integer ucoId;

    @ApiModelProperty(value = "被评论主键id")
    private Integer ucoBecomId;

    @ApiModelProperty(value = "评论人")
    private Integer userId;

    @ApiModelProperty(value = "评论内容")
    private String ucoContent;

    @ApiModelProperty(value = "评论类型1、资讯2、课程3、...")
    private Integer ucoSort;

    @ApiModelProperty(value = "用户回复总数")
    private Integer ucoReplyNum;
    
    @ApiModelProperty(value = "用户点赞数")
    private Integer ucoSupport;

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
