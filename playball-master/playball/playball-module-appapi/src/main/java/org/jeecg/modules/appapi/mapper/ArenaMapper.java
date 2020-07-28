package org.jeecg.modules.appapi.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.Arena;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 商家表
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
public interface ArenaMapper extends BaseMapper<Arena> {

    List<Arena> queryPageList(@Param("params") Map<String, Object> params);
}
