package org.jeecg.modules.appapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.entity.PlayballUserDynamic;
import org.jeecg.modules.appapi.entity.bo.PlayballUserDynamicBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserDynamicVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-23
 */
public interface PlayballUserDynamicMapper extends BaseMapper<PlayballUserDynamic> {
	//连表查询列表
	IPage<PlayballUserDynamicBo> selectDynList(IPage<PlayballUserDynamic> page,@Param("vo") PlayballUserDynamicVo vo);
	
	int updateDynamicBrowse(PlayballUserDynamic dynamic);
	
	//查询全文索引动态数据
	IPage<PlayballUserDynamicBo> findDynListFulltext(IPage<PlayballUserDynamic> page,@Param("fullText") String fullText);
	
	//根据id连表查询
	PlayballUserDynamicBo selectDynById ( PlayballUserDynamic dyn);
}
