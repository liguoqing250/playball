package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.BusinessEvaluation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.appapi.entity.bo.PlayballUserEvaluationBo;

import java.util.List;

/**
 * @Description: 商家评价
 * @Author: jeecg-boot
 * @Date:   2020-08-06
 * @Version: V1.0
 */
public interface IBusinessEvaluationService extends IService<BusinessEvaluation> {

    List<PlayballUserEvaluationBo> QueryBusinessEvaluation(String bid);
}
