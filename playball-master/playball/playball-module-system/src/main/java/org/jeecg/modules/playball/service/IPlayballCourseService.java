package org.jeecg.modules.playball.service;

import org.jeecg.modules.playball.entity.PlayballCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: cms教程
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
public interface IPlayballCourseService extends IService<PlayballCourse> {
	   
	public void deleteById(Integer id);
	
	public void updateCourseById(PlayballCourse course);
}
