package org.jeecg.common.playball.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Data
@TableName("playball_orders")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_orders对象", description="订单管理")
public class PlayballOrder {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
	private java.lang.Integer ordId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private java.lang.Integer ordUserId;
	/**商品id*/
	@Excel(name = "商品id", width = 15)
    @ApiModelProperty(value = "商品id")
	private java.lang.Integer ordGoodsId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
	private java.lang.String ordGoodsName;
	/**商品总价格*/
	@Excel(name = "商品总价格", width = 15)
    @ApiModelProperty(value = "商品总价格")
	private java.math.BigDecimal cprPrice;
	/**商品订单号*/
	@Excel(name = "商品订单号", width = 15)
    @ApiModelProperty(value = "商品订单号")
	private java.lang.String cprNumber;
	/**订单类型：1、场地预定 2、视频购买*/
	@Excel(name = "订单类型：1、场地预定 2、视频购买", width = 15)
    @ApiModelProperty(value = "订单类型：1、场地预定 2、视频购买")
	private java.lang.Integer cprType;
	/**订单状态：1、已支付 2、未支付*/
	@Excel(name = "订单状态：1、已支付 2、未支付", width = 15)
    @ApiModelProperty(value = "订单状态：1、已支付 2、未支付")
	private java.lang.String cprState;
	/**纪录创建时间*/
	@Excel(name = "纪录创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录创建时间")
	private java.util.Date createTime;
	/**纪录修改时间*/
	@Excel(name = "纪录修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录修改时间")
	private java.util.Date updateTime;
	/**逻辑删除*/
	@Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
	private java.lang.Integer isDelete;
	/**版本号（用作乐观锁）*/
	@Excel(name = "版本号（用作乐观锁）", width = 15)
    @ApiModelProperty(value = "版本号（用作乐观锁）")
	private java.lang.Integer version;
}
