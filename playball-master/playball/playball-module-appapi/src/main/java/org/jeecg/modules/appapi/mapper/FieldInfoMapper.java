package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.FieldInfo;
import org.jeecg.modules.appapi.entity.Game;
import org.jeecg.modules.appapi.entity.bo.GameBo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 场馆详情
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface FieldInfoMapper extends BaseMapper<FieldInfo> {

    List<FieldInfo> queryByMyOrders(Integer id);
}
