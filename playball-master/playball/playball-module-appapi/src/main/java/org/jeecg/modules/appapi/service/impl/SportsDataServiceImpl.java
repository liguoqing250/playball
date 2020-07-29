package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.SportsData;
import org.jeecg.modules.appapi.mapper.SportsDataMapper;
import org.jeecg.modules.appapi.service.ISportsDataService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 球员能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Service
public class SportsDataServiceImpl extends ServiceImpl<SportsDataMapper, SportsData> implements ISportsDataService {

}
