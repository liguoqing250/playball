package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballAppUser;
import org.jeecg.modules.playball.mapper.PlayballAppUserMapper;
import org.jeecg.modules.playball.service.IPlayballAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 会员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class PlayballAppUserServiceImpl extends ServiceImpl<PlayballAppUserMapper, PlayballAppUser> implements IPlayballAppUserService {
	
	@Autowired
	private PlayballAppUserMapper userMapper;
	
	@Override
	public IPage<PlayballAppUser> quereyList(IPage page, PlayballAppUser playballAppUser){
		List<PlayballAppUser> list = userMapper.getAppUserList(page, playballAppUser);
		Integer total = userMapper.getAppUserListTotal(playballAppUser);
		
		IPage<PlayballAppUser> result = new Page<>(page.getCurrent(),page.getSize(),total);
		result.setRecords(list);
		return result;
	}
}
