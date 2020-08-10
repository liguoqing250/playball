package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.EnrollPlayer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 参赛球员
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
public interface EnrollPlayerMapper extends BaseMapper<EnrollPlayer> {

    void deleteByTeamId(Integer team_id);
}
