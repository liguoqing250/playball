package org.jeecg.modules.appapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballOrders;
import org.jeecg.modules.appapi.entity.bo.PlayballOrdersBo;
import org.jeecg.modules.appapi.entity.vo.PlayballOrdersVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 订单  Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-15
 */
public interface PlayballOrdersMapper extends BaseMapper<PlayballOrders> {
	IPage<PlayballOrdersBo> getOrdersList(Page<PlayballOrders> page,@Param("o")PlayballOrdersVo orderVo);
}
