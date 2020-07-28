package org.jeecg.modules.appapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ArenaVo {
    /**最低价格*/
    private String price;
    /**距离*/
    private String distance;
    private String id;
    /**父机构ID*/
    private String parent_id;
    /**机构/部门名称*/
    private String depart_name;
    /**英文名*/
    private String depart_name_en;
    /**缩写*/
    private String depart_name_abbr;
    /**排序*/
    private Integer depart_order;
    /**描述*/
    private String description;
    /**机构类别 1组织机构，2岗位*/
    private String org_category;
    /**机构类型 1一级部门 2子部门*/
    private String org_type;
    /**机构编码*/
    private String org_code;
    /**手机号*/
    private String mobile;
    /**传真*/
    private String fax;
    /**地址*/
    private String address;
    /**备注*/
    private String memo;
    /**状态（1启用，0不启用）*/
    private String status;
    /**场馆图片*/
    private String image_url;
    /**营业时间*/
    private String open_time;
    /**打烊时间*/
    private String close_time;
    private String del_flag;
    /**创建人*/
    private String create_by;
    /**创建日期*/
    private Date create_time;
    /**更新人*/
    private String update_by;
    /**更新日期*/
    private Date update_time;
    /**所属省*/
    private Integer province;
    /**所属市区*/
    private Integer city;
    /**所属区*/
    private Integer district;
    /**商家定位经度*/
    private String location_lon;
    /**商家定位维度*/
    private String location_lat;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public String getDepart_name_en() {
        return depart_name_en;
    }

    public void setDepart_name_en(String depart_name_en) {
        this.depart_name_en = depart_name_en;
    }

    public String getDepart_name_abbr() {
        return depart_name_abbr;
    }

    public void setDepart_name_abbr(String depart_name_abbr) {
        this.depart_name_abbr = depart_name_abbr;
    }

    public Integer getDepart_order() {
        return depart_order;
    }

    public void setDepart_order(Integer depart_order) {
        this.depart_order = depart_order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrg_category() {
        return org_category;
    }

    public void setOrg_category(String org_category) {
        this.org_category = org_category;
    }

    public String getOrg_type() {
        return org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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

    public String getLocation_lon() {
        return location_lon;
    }

    public void setLocation_lon(String location_lon) {
        this.location_lon = location_lon;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }
}
