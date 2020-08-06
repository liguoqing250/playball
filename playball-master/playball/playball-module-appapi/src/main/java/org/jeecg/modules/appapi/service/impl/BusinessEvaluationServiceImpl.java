package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.BusinessEvaluation;
import org.jeecg.modules.appapi.entity.bo.PlayballUserEvaluationBo;
import org.jeecg.modules.appapi.mapper.BusinessEvaluationMapper;
import org.jeecg.modules.appapi.service.IBusinessEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 商家评价
 * @Author: jeecg-boot
 * @Date:   2020-08-06
 * @Version: V1.0
 */
@Service
public class BusinessEvaluationServiceImpl extends ServiceImpl<BusinessEvaluationMapper, BusinessEvaluation> implements IBusinessEvaluationService {
    @Autowired
    BusinessEvaluationMapper businessEvaluationMapper;


    @Override
    public List<PlayballUserEvaluationBo> QueryBusinessEvaluation(String bid) {
        return businessEvaluationMapper.QueryBusinessEvaluation(bid);
    }
}
