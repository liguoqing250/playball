package org.jeecg.modules.appapi.controller;


import java.math.BigDecimal;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballUsersBalance;
import org.jeecg.modules.appapi.mapper.PlayballUsersBalanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 *  前端控制器
 *  用户余额控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/UsersBalance")
public class PlayballUsersBalanceController {

	@Autowired
	private PlayballUsersBalanceMapper mapper;
	
	//用户登录时添加用户余额数据， 用户登录时调用接口 参数用户id 
	@PostMapping("/insertUsersBalance")
	public Result<?> insertUsersBalance(@RequestBody PlayballUsersBalance u_bal){
		//先查询用户是否已有余额表数据，检查用户余额是否存在
		PlayballUsersBalance isExist = checkUsersBalanceIsExist(u_bal);
		if(isExist!=null){
			return Result.ok("已有数据，无需插入");
		}
		//没有用户则插入新建数据
		if(u_bal.getUbUserId()!=null){
			int insert = mapper.insert(u_bal);
			if(insert>0){
				return Result.ok("添加成功");
			}else{
				return Result.error(0, "添加失败！");
			}
		}
		return Result.error(0, "操作错误！");
	}
	
	//查询用户当前余额
	@GetMapping(value="/getUsersBalance")
	public Result<?> getUsersBalance(PlayballUsersBalance u_bal){
		System.out.println("查询用户当前余额" + u_bal);
		//检查用户余额是否存在
		PlayballUsersBalance isExist = checkUsersBalanceIsExist(u_bal);
		if(isExist!=null){
			return Result.ok(isExist);
		}
		return Result.error(0, "没有该用户记录");
	}
	
	//更新用户余额充值
	@PostMapping("/updateUsersBalance")
	public Result<?> updateUsersBalance(PlayballUsersBalance u_bal){
		System.err.println("更新用户余额充值" + u_bal);
		//检查用户余额是否存在
		PlayballUsersBalance isExist = checkUsersBalanceIsExist(u_bal);
		if(isExist==null){
			return Result.error(0, "用户不存在");
		}
		//判断余额加上当前金额是否大于0
		BigDecimal balance = isExist.getUbBalance().add(u_bal.getUbBalance());
		//判断余额是否为整数在操作
		if(balance.compareTo(BigDecimal.ZERO) == -1){
			return Result.error(0, "参数不匹配");
		}
		//重置参数
		u_bal.setUbId(isExist.getUbId());//设置主键id
		u_bal.setUbBalance(balance);//设置余额
		int updateById = mapper.updateById(u_bal);
		return Result.ok(updateById>0? "操作成功":"操作失败");
	}
	
	//更新用户余额提现
	@PostMapping("/updateWitUsersBalance")
	public Result<?> updateWitUsersBalance(PlayballUsersBalance u_bal){
		System.err.println("更新用户余额提现" + u_bal);
		//检查用户余额是否存在
		PlayballUsersBalance isExist = checkUsersBalanceIsExist(u_bal);
		if(isExist==null){
			return Result.error(0, "用户不存在");
		}
		//判断余额加上当前金额是否大于0
		BigDecimal balance = isExist.getUbBalance().subtract(u_bal.getUbBalance());
		//判断余额是否为整数在操作
		if(balance.compareTo(BigDecimal.ZERO) == -1){
			return Result.error(0, "参数不匹配");
		}
		//重置参数
		u_bal.setUbId(isExist.getUbId());//设置主键id
		u_bal.setUbBalance(balance);//设置余额
		int updateById = mapper.updateById(u_bal);
		return Result.ok(updateById>0? "操作成功":"操作失败");
	}
	
	
	//检查用户是否存在
	public PlayballUsersBalance checkUsersBalanceIsExist(PlayballUsersBalance u_bal){
		QueryWrapper<PlayballUsersBalance> queryWrapper = new QueryWrapper<PlayballUsersBalance>();
			queryWrapper.eq("ub_user_id", u_bal.getUbUserId());
			return mapper.selectOne(queryWrapper);
	}
	
}

