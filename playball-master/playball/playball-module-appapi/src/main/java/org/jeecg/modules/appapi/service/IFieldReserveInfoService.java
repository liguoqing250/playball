package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.FieldBookable;
import org.jeecg.modules.appapi.entity.FieldReserveInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * @Description: 场地预定
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IFieldReserveInfoService extends IService<FieldReserveInfo> {
    List<FieldBookable> queryFieldBookable(FieldReserveInfo fieldReserveInfo);

    List<FieldReserveInfo> queryFieldReserveInfo(String bid, String reserveTime,Integer stId);
}
