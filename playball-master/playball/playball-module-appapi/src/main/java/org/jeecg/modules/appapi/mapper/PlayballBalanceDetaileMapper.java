package org.jeecg.modules.appapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.PlayballBalanceDetaile;
import org.jeecg.modules.appapi.entity.bo.PlayballBalanceDetaileBo;
import org.jeecg.modules.appapi.entity.vo.PlayballBalanceDetaileVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-07-21
 */
public interface PlayballBalanceDetaileMapper extends BaseMapper<PlayballBalanceDetaile> {
	IPage<PlayballBalanceDetaileBo> selectBalance(IPage<PlayballBalanceDetaile> page,@Param("vo") PlayballBalanceDetaileVo vo);
}
