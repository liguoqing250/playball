package org.jeecg.modules.appapi.entity;

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
 * @Description: 商家表
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Data
@TableName("sys_depart")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart对象", description="商家表")
public class Arena {
    
	/**ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
	private String id;
	/**父机构ID*/
	@Excel(name = "父机构ID", width = 15)
    @ApiModelProperty(value = "父机构ID")
	private String parentId;
	/**机构/部门名称*/
	@Excel(name = "机构/部门名称", width = 15)
    @ApiModelProperty(value = "机构/部门名称")
	private String departName;
	/**英文名*/
	@Excel(name = "英文名", width = 15)
    @ApiModelProperty(value = "英文名")
	private String departNameEn;
	/**缩写*/
	@Excel(name = "缩写", width = 15)
    @ApiModelProperty(value = "缩写")
	private String departNameAbbr;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
	private Integer departOrder;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
	private String description;
	/**机构类别 1组织机构，2岗位*/
	@Excel(name = "机构类别 1组织机构，2岗位", width = 15)
    @ApiModelProperty(value = "机构类别 1组织机构，2岗位")
	private String orgCategory;
	/**机构类型 1一级部门 2子部门*/
	@Excel(name = "机构类型 1一级部门 2子部门", width = 15)
    @ApiModelProperty(value = "机构类型 1一级部门 2子部门")
	private String orgType;
	/**机构编码*/
	@Excel(name = "机构编码", width = 15)
    @ApiModelProperty(value = "机构编码")
	private String orgCode;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
	private String mobile;
	/**传真*/
	@Excel(name = "传真", width = 15)
    @ApiModelProperty(value = "传真")
	private String fax;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
	private String address;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
	private String memo;
	/**状态（1启用，0不启用）*/
	@Excel(name = "状态（1启用，0不启用）", width = 15)
    @ApiModelProperty(value = "状态（1启用，0不启用）")
	private String status;
	/**场馆图片*/
	@Excel(name = "场馆图片", width = 15)
    @ApiModelProperty(value = "场馆图片")
	private String imageUrl;
	/**营业时间*/
	@Excel(name = "营业时间", width = 15)
    @ApiModelProperty(value = "营业时间")
	private String openTime;
	/**打烊时间*/
	@Excel(name = "打烊时间", width = 15)
    @ApiModelProperty(value = "打烊时间")
	private String closeTime;
	/**银行卡号*/
	@Excel(name = "银行卡号", width = 15)
    @ApiModelProperty(value = "银行卡号")
	private String bankCard;
	/**删除状态（0，正常，1已删除）*/
	@Excel(name = "删除状态（0，正常，1已删除）", width = 15)
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
	private String delFlag;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private Date updateTime;
	/**所属省*/
	@Excel(name = "所属省", width = 15)
    @ApiModelProperty(value = "所属省")
	private Integer province;
	/**所属市区*/
	@Excel(name = "所属市区", width = 15)
    @ApiModelProperty(value = "所属市区")
	private Integer city;
	/**所属区*/
	@Excel(name = "所属区", width = 15)
    @ApiModelProperty(value = "所属区")
	private Integer district;
	/**商家定位经度*/
	@Excel(name = "商家定位经度", width = 15)
    @ApiModelProperty(value = "商家定位经度")
	private String locationLon;
	/**商家定位维度*/
	@Excel(name = "商家定位维度", width = 15)
    @ApiModelProperty(value = "商家定位维度")
	private String locationLat;
	/**商家平均评分*/
	@Excel(name = "商家平均评分", width = 15)
    @ApiModelProperty(value = "商家平均评分")
	private String avgscore;

	public String getAvgscore() {
		return avgscore;
	}

	public void setAvgscore(String avgscore) {
		this.avgscore = avgscore;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getDepartNameEn() {
		return departNameEn;
	}

	public void setDepartNameEn(String departNameEn) {
		this.departNameEn = departNameEn;
	}

	public String getDepartNameAbbr() {
		return departNameAbbr;
	}

	public void setDepartNameAbbr(String departNameAbbr) {
		this.departNameAbbr = departNameAbbr;
	}

	public Integer getDepartOrder() {
		return departOrder;
	}

	public void setDepartOrder(Integer departOrder) {
		this.departOrder = departOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrgCategory() {
		return orgCategory;
	}

	public void setOrgCategory(String orgCategory) {
		this.orgCategory = orgCategory;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public String getLocationLon() {
		return locationLon;
	}

	public void setLocationLon(String locationLon) {
		this.locationLon = locationLon;
	}

	public String getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(String locationLat) {
		this.locationLat = locationLat;
	}
}
