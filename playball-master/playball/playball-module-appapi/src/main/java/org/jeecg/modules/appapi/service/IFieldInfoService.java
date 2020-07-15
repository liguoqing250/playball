package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.FieldInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 场馆详情
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IFieldInfoService extends IService<FieldInfo> {

    List<FieldInfo> listMaps(Map<String, Object> map);
}
