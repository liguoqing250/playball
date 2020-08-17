package org.jeecg.modules.appapi.controller;


import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballOrders;
import org.jeecg.modules.appapi.entity.bo.PlayballOrdersBo;
import org.jeecg.modules.appapi.entity.vo.PlayballOrdersVo;
import org.jeecg.modules.appapi.mapper.PlayballOrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  订单表前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-15
 */
@RestController
@RequestMapping("/playballOrders")
public class PlayballOrdersController {

	@Autowired
	private PlayballOrdersMapper mapper;
	
	/* APP 调用添加订单数据*/
	@PostMapping("/addPlayballOrders")
	public Result<JSONObject> addPlayballOrders(@RequestBody PlayballOrders ord){
		//返回对象
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		int insert = mapper.insert(ord);
		System.err.println("添加订单数据" + ord);
		obj.put("data", ord);
		if(insert>0){
			result.setCode(200);
			result.setResult(obj);
		}else{
			result.setCode(0);
		}
		return result;
	}
	
	//查询用户订单数据
	@GetMapping("/getOrders")
	public Result<?> getOrders(PlayballOrdersVo ordersVo){
		Page<PlayballOrders> page = new Page<PlayballOrders>(ordersVo.getPage(), ordersVo.getLimit());
		IPage<PlayballOrdersBo> ordersList = mapper.getOrdersList(page, ordersVo);
		return Result.ok(ordersList);
	}
}

