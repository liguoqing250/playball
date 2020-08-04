package org.jeecg.modules.appapi.mapper;

import org.jeecg.modules.appapi.entity.PlayballNews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 资讯表 Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-30
 */
public interface NewsMapper extends BaseMapper<PlayballNews> {
	int updateNewsBrowse(PlayballNews news);
}
