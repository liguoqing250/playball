package org.jeecg.modules.appapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballNews;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
	
	//查询全文索引资讯数据
	IPage<PlayballNews> findNewsFulltext(IPage<PlayballNews> page,@Param("fullText") String fullText);
}
