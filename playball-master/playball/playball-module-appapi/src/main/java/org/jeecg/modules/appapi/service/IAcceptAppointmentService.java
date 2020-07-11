package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.AcceptAppointment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 应约
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IAcceptAppointmentService extends IService<AcceptAppointment> {

    boolean isJoinAppointment(Integer id);
}
