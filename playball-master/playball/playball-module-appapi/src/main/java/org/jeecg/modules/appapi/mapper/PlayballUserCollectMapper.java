package org.jeecg.modules.appapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballUserCollect;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCollectBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserCollectVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 用户收藏表 Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-08
 */
public interface PlayballUserCollectMapper extends BaseMapper<PlayballUserCollect> {
	//更新收藏删除状态
	int updateStart(Integer id);
	
	PlayballUserCollect selectIsEntity(PlayballUserCollect coll);
	
	//连表查询用户收藏
	IPage<PlayballUserCollectBo> selectUserCollect(IPage<PlayballUserCollect> page,@Param("vo") PlayballUserCollectVo collVo);
}
