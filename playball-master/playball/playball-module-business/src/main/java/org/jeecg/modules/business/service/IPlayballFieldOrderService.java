package org.jeecg.modules.business.service;

import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
public interface IPlayballFieldOrderService extends IService<PlayballOrder> {
	public IPage<PlayballOrderModel> queryFieldOrderByUserId(IPage page, PlayballOrderModel playballOrderModel, String userId);
}
