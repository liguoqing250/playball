package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.modules.playball.entity.PlayballEnroll;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.mapper.PlayballEnrollMapper;
import org.jeecg.modules.playball.mapper.PlayballGameMapper;
import org.jeecg.modules.playball.service.IPlayballEnrollService;
import org.jeecg.modules.playball.vo.PlayballEnrollPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 报名球队管理
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Service
public class PlayballEnrollServiceImpl extends ServiceImpl<PlayballEnrollMapper, PlayballEnroll> implements IPlayballEnrollService {

	@Autowired
	private PlayballEnrollMapper enrollMapper;
	
	@Override
	public IPage<PlayballEnrollPage> queryEnrollList(IPage page, PlayballEnrollPage enrollPage){
		
		List<PlayballEnrollPage> list = enrollMapper.getEnrollList(page,enrollPage);
		Integer total = enrollMapper.getEnrollListTotal(enrollPage);
	       
		IPage<PlayballEnrollPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	public List<PlayballTeam> getEnrollTeamByGamesId(Integer gamesId){
		
		return enrollMapper.getEnrollTeamByGamesId(gamesId);
	}
}
