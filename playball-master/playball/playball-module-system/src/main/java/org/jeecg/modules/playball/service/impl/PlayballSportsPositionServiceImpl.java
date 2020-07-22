package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballSportsPosition;
import org.jeecg.common.playball.vo.PlayballSportsPositionModel;
import org.jeecg.modules.playball.mapper.PlayballSportsPositionMapper;
import org.jeecg.modules.playball.service.IPlayballSportsPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 担任位置
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
@Service
public class PlayballSportsPositionServiceImpl extends ServiceImpl<PlayballSportsPositionMapper, PlayballSportsPosition> implements IPlayballSportsPositionService {
	@Autowired
	private PlayballSportsPositionMapper positionMapper;
	
	@Override
	public IPage<PlayballSportsPositionModel> queryPositionList(IPage page, PlayballSportsPositionModel positionModel){
		List<PlayballSportsPositionModel> list = positionMapper.getPositionList(page,positionModel);
		Integer total = positionMapper.getPositionListTotal(positionModel);
	       
		IPage<PlayballSportsPositionModel> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	public List<PlayballSportsPositionModel> getPositionListBySportsId(Integer sportsId){
		return positionMapper.getPositionListBySportsId(sportsId);
	}
}
