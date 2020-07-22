package org.jeecg.modules.playball.service;

import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
public interface IPlayballOrderService extends IService<PlayballOrder> {
	
	public IPage<PlayballOrderModel> queryOrderList(IPage page, PlayballOrderModel playballOrderModel);
}
