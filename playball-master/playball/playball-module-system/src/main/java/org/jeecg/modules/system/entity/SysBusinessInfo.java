package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 球馆表
 * <p>
 * 
 * @Author Steve
 * @Since  2019-01-22
 */
@Data
@TableName("playball_business_info")
public class SysBusinessInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 场馆名
     */
    @Excel(name = "场馆名", width = 15)
    private java.lang.String bName;
    
    /**
     * 开始时间
     */
    //@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date openTime;
    /**
     * 结束时间
     */
    //@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date closeTime;
    
    /**
     * 商家账户
     */
    @Excel(name = "商家账户", width = 15)
    private java.lang.String bAccount;
    
    /**
     * 商家账户
     */
    private java.lang.String bPassWord;
    
    /**
     * 所属省
     */
    private java.lang.String bProvince;
    
    /**
     * 所属市
     */
    private java.lang.String bCity;
    
    /**
     * 所属区
     */
    private java.lang.String bDistrict;
    
    /**
     * 
     */
    private java.lang.String bAddress;
    
    /**
     * 头像地址
     */
    private java.lang.String bHeadAddress;
    
    /**
     * 电话
     */
    @Excel(name = "电话号码", width = 15)
    private java.lang.String bPhoneNumber;
    
    /**
     * 说明备注
     */
    private java.lang.String bExplain;
    
    /**
     * 银行卡
     */
    private java.lang.String bBankCard;
    
    /**
     * 删除状态（0，正常，1已删除）
     */
    private java.lang.String delFlag;
  
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
   
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    
    /**
     * 版本号
     */
    private java.lang.String version;
  
}