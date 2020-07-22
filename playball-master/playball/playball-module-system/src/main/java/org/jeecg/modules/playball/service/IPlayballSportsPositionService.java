package org.jeecg.modules.playball.service;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballSportsPosition;
import org.jeecg.common.playball.vo.PlayballSportsPositionModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 担任位置
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
public interface IPlayballSportsPositionService extends IService<PlayballSportsPosition> {
	public IPage<PlayballSportsPositionModel> queryPositionList(IPage page, PlayballSportsPositionModel positionModel);
	
	public List<PlayballSportsPositionModel> getPositionListBySportsId(Integer sportsId);
}
