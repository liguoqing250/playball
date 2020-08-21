package org.jeecg.modules.appapi.controller;

import java.util.List;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballBalanceDetaile;
import org.jeecg.modules.appapi.entity.bo.PlayballBalanceDetaileBo;
import org.jeecg.modules.appapi.entity.vo.PlayballBalanceDetaileVo;
import org.jeecg.modules.appapi.mapper.PlayballBalanceDetaileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  前端控制器
 *  用户余额明细控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/BalanceDetaile")
public class PlayballBalanceDetaileController {

	@Autowired
	private PlayballBalanceDetaileMapper mapper;
	
	//添加用户余额明细数据
	@PostMapping("/addBalanceDetaile")
	public Result<?> addBalanceDetaile(PlayballBalanceDetaile u_deta){
		int insert = mapper.insert(u_deta);
		if(insert>0){
			return Result.ok("添加成功");
		}
		return Result.error(0, "添加失败");
	}
	
	//查询余额明细数据
	@GetMapping("/getBalanceDetaile")
	public Result<?> getBalanceDetaile(PlayballBalanceDetaileVo balVo){
		Page<PlayballBalanceDetaile> page = new Page<PlayballBalanceDetaile>(balVo.getPage(), balVo.getLimit());
		IPage<PlayballBalanceDetaileBo> selectPage = mapper.selectBalance(page, balVo);
		return Result.ok(selectPage);
	}
	
	@GetMapping("/BalanceDetaile")
	public Result<?> BalanceDetaile(PlayballBalanceDetaileVo balVo){
		List<PlayballBalanceDetaile> list = mapper.selectList(null);
		return Result.ok(list);
	}
}

