package org.jeecg.modules.business.service;

import java.util.List;
import org.jeecg.common.playball.entity.PlayballFieldInfo;
import org.jeecg.common.playball.vo.PlayballFieldInfoModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 场地信息
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
public interface IPlayballFieldInfoService extends IService<PlayballFieldInfo> {
	
	public IPage<PlayballFieldInfoModel> queryFieldInfoByUserId(IPage page, PlayballFieldInfoModel playballFieldInfo, String userId);
	
	public List<PlayballFieldInfoModel> getFieldInfoBySportsId( Integer sportsId);
	
	public String getBusinessIdByUserId(String userId);
}
