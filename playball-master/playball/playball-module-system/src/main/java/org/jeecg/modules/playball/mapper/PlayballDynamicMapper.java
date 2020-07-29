package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballDynamic;
import org.jeecg.common.playball.vo.PlayballDynamicModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 用户动态发布
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
public interface PlayballDynamicMapper extends BaseMapper<PlayballDynamic> {
	
	public List<PlayballDynamicModel> getDynamicList(IPage page, @Param("playballDynamicModel")PlayballDynamicModel playballDynamicModel);
	
	public Integer getDynamicListTotal(@Param("playballDynamicModel")PlayballDynamicModel playballDynamicModel);
}
