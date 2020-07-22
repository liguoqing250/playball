package org.jeecg.modules.playball.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
public interface PlayballOrderMapper extends BaseMapper<PlayballOrder> {
	public List<PlayballOrderModel> getOrderList(IPage page, @Param("playballOrderModel")PlayballOrderModel playballOrderModel);
	public Integer getOrderListTotal(@Param("playballOrderModel")PlayballOrderModel  playballOrderModel);
}
