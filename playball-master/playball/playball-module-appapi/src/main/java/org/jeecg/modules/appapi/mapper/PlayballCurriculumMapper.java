package org.jeecg.modules.appapi.mapper;

import org.jeecg.modules.appapi.entity.PlayballCurriculum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-08
 */
public interface PlayballCurriculumMapper extends BaseMapper<PlayballCurriculum> {
	int updateHots(Integer id); //更新浏览量
	
	int updateCurriculumBrowse(PlayballCurriculum cur); //更新点赞/评论/收藏数
}
