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
 * @author 予之文化传媒公司
 * @since 2020-07-21
 * 用户余额明细实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballBalanceDetaile对象", description="")
public class PlayballBalanceDetaile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "余额明细表主键id")
    @TableId(value = "bde_id", type = IdType.AUTO)
    private Integer bdeId;

    @ApiModelProperty(value = "用户余额表主键id")
    private Integer bdeUbId;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal bdeConsume;

    @ApiModelProperty(value = "订单编号")
    private String bdeNumber;

    @ApiModelProperty(value = "消费名称")
    private String bdeConsumeName;

    @ApiModelProperty(value = "支付方式 1 、微信 2、支付宝")
    private Integer bdePayMethod;

    @ApiModelProperty(value = "支付状态 1 、支付成功 2、支付失败")
    private Integer bdePayState;
    
    @ApiModelProperty(value = "充值提现  1、充值 2、提现")
    private Integer bdeRecWit;

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
