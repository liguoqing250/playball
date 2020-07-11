package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.SportsType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动类型表
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface SportsTypeMapper extends BaseMapper<SportsType> {
    List<SportsType> getSportsType();
    List<SportsType> querySportsTypeByBusinessId(String id);
}
