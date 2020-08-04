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
 * 用户回复表
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballUserReply对象", description="用户回复表")
public class PlayballUserReply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户回复表主键id")
    @TableId(value = "ure_id", type = IdType.AUTO)
    private Integer ureId;

    @ApiModelProperty(value = "关联评价表主键")
    private Integer ureUcoid;

    @ApiModelProperty(value = "回复人")
    private Integer userId;

    @ApiModelProperty(value = "回复内容")
    private String ureContent;

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
