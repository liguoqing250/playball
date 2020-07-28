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
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballOrders对象", description="")
public class PlayballOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "ord_id", type = IdType.AUTO)
    private Integer ordId;

    @ApiModelProperty(value = "用户id")
    private Integer ordUserId;

    @ApiModelProperty(value = "商品id")
    private Integer ordGoodsId;

    @ApiModelProperty(value = "商品名称")
    private String ordGoodsName;

    @ApiModelProperty(value = "商品总价格")
    private BigDecimal cprPrice;

    @ApiModelProperty(value = "商品订单号")
    private String cprNumber;

    @ApiModelProperty(value = "订单类型：1、场地预定 2、视频购买")
    private Integer cprType;

    @ApiModelProperty(value = "订单状态：1、已支付 2、未支付")
    private String cprState;

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
