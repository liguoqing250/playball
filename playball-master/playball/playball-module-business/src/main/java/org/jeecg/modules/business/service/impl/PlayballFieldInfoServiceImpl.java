package org.jeecg.modules.business.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballFieldInfo;
import org.jeecg.common.playball.vo.PlayballFieldInfoModel;
import org.jeecg.modules.business.mapper.PlayballFieldInfoMapper;
import org.jeecg.modules.business.service.IPlayballFieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 场地信息
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Service
public class PlayballFieldInfoServiceImpl extends ServiceImpl<PlayballFieldInfoMapper, PlayballFieldInfo> implements IPlayballFieldInfoService {
	
	@Autowired
	PlayballFieldInfoMapper fieldInfoMapper;
	
	@Override
	public IPage<PlayballFieldInfoModel> queryFieldInfoByUserId(IPage page, PlayballFieldInfoModel playballFieldInfo, String userId){
		
		List<PlayballFieldInfoModel> list = fieldInfoMapper.getFieldInfoListByUserId(page,playballFieldInfo,userId);
		Integer total = fieldInfoMapper.getFieldInfoListByUserIdTotal(playballFieldInfo,userId);
	       
		IPage<PlayballFieldInfoModel> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	public List<PlayballFieldInfoModel> getFieldInfoBySportsId(Integer sportsId){

		return fieldInfoMapper.getFieldInfoBySportsId(sportsId);
	}
	
	@Override
	public String getBusinessIdByUserId(String userId) {
		return fieldInfoMapper.getBusinessIdByUserId(userId);
	}
}
