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
 * 资讯表
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlayballNews对象", description="资讯表")
public class PlayballNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面图片")
    private String image;

    @ApiModelProperty(value = "详细内容")
    private String content;

    @ApiModelProperty(value = "评论量")
    private Integer commentTotal;

    @ApiModelProperty(value = "浏览量")
    private Integer pageView;

    @ApiModelProperty(value = "新闻的类型，对应运动类型")
    private Integer newsType;

    @ApiModelProperty(value = "标签：1 图片 2、视频")
    private Integer label;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "删除标志")
    private Integer delFlg;

    @Version
    private Integer version;

    
    
}
