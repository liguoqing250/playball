package org.jeecg.modules.business.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;
import org.jeecg.modules.business.mapper.PlayballFieldOrderMapper;
import org.jeecg.modules.business.service.IPlayballFieldOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Service
public class PlayballFieldOrderServiceImpl extends ServiceImpl<PlayballFieldOrderMapper, PlayballOrder> implements IPlayballFieldOrderService {
	
	@Autowired
	private PlayballFieldOrderMapper orderMapper;
	
	@Override
	public IPage<PlayballOrderModel> queryFieldOrderByUserId(IPage page, PlayballOrderModel playballOrderModel, String userId){
		
		List<PlayballOrderModel> list = orderMapper.getFieldOrderList(page,playballOrderModel,userId);
		Integer total = orderMapper.getFieldOrderListTotal(playballOrderModel,userId);
	       
		IPage<PlayballOrderModel> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
}
