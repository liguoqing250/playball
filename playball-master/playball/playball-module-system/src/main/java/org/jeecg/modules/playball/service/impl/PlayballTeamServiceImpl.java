package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.common.playball.vo.PlayballTeamModel;
import org.jeecg.modules.playball.mapper.PlayballTeamMapper;
import org.jeecg.modules.playball.service.IPlayballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 球队
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Service
public class PlayballTeamServiceImpl extends ServiceImpl<PlayballTeamMapper, PlayballTeam> implements IPlayballTeamService {
	
	@Autowired
	private PlayballTeamMapper teamMapper;
	
	@Override
	public IPage<PlayballTeamModel> queryTeamList(IPage page, PlayballTeamModel playballTeam){
		List<PlayballTeamModel> list = teamMapper.getTeamList(page, playballTeam);
		Integer total = teamMapper.getTeamListTotal(playballTeam);
		
		IPage<PlayballTeamModel> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);
		
		return result;
	}
}
