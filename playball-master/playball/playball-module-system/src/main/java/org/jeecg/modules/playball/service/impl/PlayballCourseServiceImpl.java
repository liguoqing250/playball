package org.jeecg.modules.playball.service.impl;

import org.jeecg.modules.playball.entity.PlayballCourse;
import org.jeecg.modules.playball.mapper.PlayballCourseMapper;
import org.jeecg.modules.playball.service.IPlayballCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: cms教程
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Service
public class PlayballCourseServiceImpl extends ServiceImpl<PlayballCourseMapper, PlayballCourse> implements IPlayballCourseService {
	@Autowired
	PlayballCourseMapper courseMapper;
	
	@Override
	public void deleteById(Integer id) {
		courseMapper.deleteById(id);
	}

	@Override
	public void updateCourseById(PlayballCourse course) {
		courseMapper.updateCourseById(course);
	}
}
