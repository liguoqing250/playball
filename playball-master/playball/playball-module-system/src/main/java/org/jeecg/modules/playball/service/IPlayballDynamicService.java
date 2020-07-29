package org.jeecg.modules.playball.service;

import org.jeecg.common.playball.entity.PlayballDynamic;
import org.jeecg.common.playball.vo.PlayballDynamicModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 用户动态发布
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
public interface IPlayballDynamicService extends IService<PlayballDynamic> {
	public IPage<PlayballDynamicModel> queryDynamicList(IPage page, PlayballDynamicModel playballDynamicModel);
}
