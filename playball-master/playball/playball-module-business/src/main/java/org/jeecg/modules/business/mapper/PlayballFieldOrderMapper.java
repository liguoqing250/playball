package org.jeecg.modules.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
public interface PlayballFieldOrderMapper extends BaseMapper<PlayballOrder> {
	
	public List<PlayballOrderModel> getFieldOrderList(IPage page, @Param("playballOrderModel")PlayballOrderModel playballOrderModel, @Param("userId")String userId);
	
	public Integer getFieldOrderListTotal(@Param("playballOrderModel") PlayballOrderModel playballOrderModel, @Param("userId")String userId);
}
