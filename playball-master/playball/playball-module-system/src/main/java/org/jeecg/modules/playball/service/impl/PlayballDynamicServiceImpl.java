package org.jeecg.modules.playball.service.impl;

import java.util.List;
import org.jeecg.common.playball.entity.PlayballDynamic;
import org.jeecg.common.playball.vo.PlayballDynamicModel;
import org.jeecg.modules.playball.mapper.PlayballDynamicMapper;
import org.jeecg.modules.playball.service.IPlayballDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户动态发布
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Service
public class PlayballDynamicServiceImpl extends ServiceImpl<PlayballDynamicMapper, PlayballDynamic> implements IPlayballDynamicService {
	
	@Autowired
	private PlayballDynamicMapper dynamicMapper;
	
	@Override
	public IPage<PlayballDynamicModel> queryDynamicList(IPage page, PlayballDynamicModel playballDynamicModel){
		List<PlayballDynamicModel> list = dynamicMapper.getDynamicList(page, playballDynamicModel);
		Integer total = dynamicMapper.getDynamicListTotal(playballDynamicModel);
		
		IPage<PlayballDynamicModel> result = new Page<>(page.getCurrent(),page.getSize(), total);
		result.setRecords(list);
		
		return result;
	}
}
