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
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 会员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Data
@TableName("playball_users")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_users对象", description="会员管理")
public class PlayballAppUser {
    
	/**uId*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "uId", width = 15)
    @ApiModelProperty(value = "uId")
	@JsonProperty("uId") 
	private java.lang.Integer uId;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
	@JsonProperty("uName") 
	private java.lang.String uName;
	/**昵称*/
	@Excel(name = "昵称", width = 15)
    @ApiModelProperty(value = "昵称")
	@JsonProperty("uNickname") 
	private java.lang.String uNickname;
	/**手机号--唯一约束*/
	@Excel(name = "手机号--唯一约束", width = 15)
    @ApiModelProperty(value = "手机号--唯一约束")
	@JsonProperty("uPhonenumber") 
	private java.lang.String uPhonenumber;
	/**密码，非空约束*/
	@Excel(name = "密码，非空约束", width = 15)
    @ApiModelProperty(value = "密码，非空约束")
	@JsonProperty("uPassworld")
	private java.lang.String uPassworld;
	/**性别 1男/ 2女*/
	@Excel(name = "性别 1男/ 2女", width = 15)
    @ApiModelProperty(value = "性别 1男/ 2女")
	@JsonProperty("uSex")
	private java.lang.Integer uSex;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
	@JsonProperty("uAge")
	private java.lang.String uAge;
	/**身高 单位 cm*/
	@Excel(name = "身高 单位 cm", width = 15)
    @ApiModelProperty(value = "身高 单位 cm")
	@JsonProperty("uHeight")
	private java.lang.Integer uHeight;
	/**体重 单位 kg*/
	@Excel(name = "体重 单位 kg", width = 15)
    @ApiModelProperty(value = "体重 单位 kg")
	@JsonProperty("uWeight")
	private java.lang.Integer uWeight;
	/**头像*/
	@Excel(name = "头像", width = 15)
    @ApiModelProperty(value = "头像")
	@JsonProperty("uHeadimage")
	private java.lang.String uHeadimage;
	/**1 手机 2QQ   3微信*/
	@Excel(name = "1 手机 2QQ   3微信", width = 15)
    @ApiModelProperty(value = "1 手机 2QQ   3微信")
	@JsonProperty("uRegisterway")
	private java.lang.Integer uRegisterway;
	/**QQ--唯一约束*/
	@Excel(name = "QQ--唯一约束", width = 15)
    @ApiModelProperty(value = "QQ--唯一约束")
	@JsonProperty("wxOpenid")
	private java.lang.String wxOpenid;
	/**微信--唯一约束*/
	@Excel(name = "微信--唯一约束", width = 15)
    @ApiModelProperty(value = "微信--唯一约束")
	@JsonProperty("qqOpenid")
	private java.lang.String qqOpenid;
	/**支付宝账号*/
	@Excel(name = "支付宝账号", width = 15)
    @ApiModelProperty(value = "支付宝账号")
	@JsonProperty("uAlipayid")
	private java.lang.String uAlipayid;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    @ApiModelProperty(value = "身份证号")
	@JsonProperty("uIdcard")
	private java.lang.String uIdcard;
	/**所属省--对应地区表主键*/
	@Excel(name = "所属省--对应地区表主键", width = 15)
    @ApiModelProperty(value = "所属省--对应地区表主键")
	@JsonProperty("uProvince")
	private java.lang.Integer uProvince;
	/**所属市--对应地区表主键*/
	@Excel(name = "所属市--对应地区表主键", width = 15)
    @ApiModelProperty(value = "所属市--对应地区表主键")
	@JsonProperty("uCity")
	private java.lang.Integer uCity;
	/**所属区--对应地区表主键*/
	@Excel(name = "所属区--对应地区表主键", width = 15)
    @ApiModelProperty(value = "所属区--对应地区表主键")
	@JsonProperty("uDistrict")
	private java.lang.Integer uDistrict;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
	@JsonProperty("uAddress")
	private java.lang.String uAddress;
	/**最后登录时间*/
	@Excel(name = "最后登录时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后登录时间")
	@JsonProperty("uLastloginTime")
	private java.util.Date uLastloginTime;
	/**个人简介*/
	@Excel(name = "个人简介", width = 15)
    @ApiModelProperty(value = "个人简介")
	@JsonProperty("uProfile")
	private java.lang.String uProfile;
	/**邀请人()*/
	@Excel(name = "邀请人()", width = 15)
    @ApiModelProperty(value = "邀请人()")
	@JsonProperty("uInvitedUser")
	private java.lang.Integer uInvitedUser;
	/**数据创建时间*/
	@Excel(name = "数据创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "数据创建时间")
	@JsonProperty("createtime")
	private java.util.Date createtime;
	/**数据更新时间*/
	@Excel(name = "数据更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "数据更新时间")
	@JsonProperty("updatetime")
	private java.util.Date updatetime;
	/**是否已删除1删除  0未删除 默认0 */
	@Excel(name = "是否已删除1删除  0未删除 默认0 ", width = 15)
    @ApiModelProperty(value = "是否已删除1删除  0未删除 默认0 ")
	@JsonProperty("isDelete")
	private java.lang.Integer isDelete;
	/**版本号（用作乐观锁）*/
	@Excel(name = "版本号（用作乐观锁）", width = 15)
    @ApiModelProperty(value = "版本号（用作乐观锁）")
	@JsonProperty("version")
	private java.lang.String version;
}
