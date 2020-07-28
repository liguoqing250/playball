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
 * @since 2020-07-21
 * 用户余额实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballUsersBalance对象", description="")
public class PlayballUsersBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "余额表主键id")
    @TableId(value = "ub_id", type = IdType.AUTO)
    private Integer ubId;

    @ApiModelProperty(value = "用户表主键id")
    private Integer ubUserId;

    @ApiModelProperty(value = "余额金额")
    private BigDecimal ubBalance;

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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUbId() {
        return ubId;
    }

    public void setUbId(Integer ubId) {
        this.ubId = ubId;
    }

    public Integer getUbUserId() {
        return ubUserId;
    }

    public void setUbUserId(Integer ubUserId) {
        this.ubUserId = ubUserId;
    }

    public BigDecimal getUbBalance() {
        return ubBalance;
    }

    public void setUbBalance(BigDecimal ubBalance) {
        this.ubBalance = ubBalance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
