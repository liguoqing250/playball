package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.BusinessEvaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.appapi.entity.bo.PlayballUserEvaluationBo;

/**
 * @Description: 商家评价
 * @Author: jeecg-boot
 * @Date:   2020-08-06
 * @Version: V1.0
 */
public interface BusinessEvaluationMapper extends BaseMapper<BusinessEvaluation> {
    List<PlayballUserEvaluationBo> QueryBusinessEvaluation(String id);

    BusinessEvaluation QueryBusinessEvaluationByOrdId(String ordId);

    String QueryBusinessEvaluationScore(String bid);
}
