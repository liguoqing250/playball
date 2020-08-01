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
 * 广告表
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballPoster对象", description="广告表")
public class PlayballPoster implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告表主键id")
    @TableId(value = "pos_id", type = IdType.AUTO)
    private Integer posId;

    @ApiModelProperty(value = "广告描述")
    private String posDescribe;

    @ApiModelProperty(value = "广告轮播图")
    private String posRotation;

    @ApiModelProperty(value = "广告类型:1、web页面 2、App本地页面")
    private Integer posType;

    @ApiModelProperty(value = "跳转路径：web跳转用户输入、App 跳转自动填充")
    private String posJumpUrl;

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
