package org.jeecg.modules.appapi.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import org.jeecg.modules.appapi.entity.FieldReserveInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 场地预定
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface FieldReserveInfoMapper extends BaseMapper<FieldReserveInfo> {
    List<FieldReserveInfo> queryFieldReserveInfo(@Param("params") Map<String, Object> map);

    List<FieldReserveInfo> queryFieldBookable(@Param("params") Map<String, Object> map);
}
