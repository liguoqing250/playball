package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;
import org.jeecg.modules.playball.mapper.PlayballOrderMapper;
import org.jeecg.modules.playball.service.IPlayballOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
@Service
public class PlayballOrderServiceImpl extends ServiceImpl<PlayballOrderMapper, PlayballOrder> implements IPlayballOrderService {
	
	@Autowired
	private PlayballOrderMapper orderMapper;

	public IPage<PlayballOrderModel> queryOrderList(IPage page, PlayballOrderModel playballOrderModel){
		List<PlayballOrderModel> list = orderMapper.getOrderList(page,playballOrderModel);
		Integer total = orderMapper.getOrderListTotal(playballOrderModel);
	       
		IPage<PlayballOrderModel> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
}
