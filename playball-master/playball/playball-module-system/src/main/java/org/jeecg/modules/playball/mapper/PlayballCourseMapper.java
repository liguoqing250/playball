package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: cms教程
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
public interface PlayballCourseMapper extends BaseMapper<PlayballCourse> {
	
	public void deleteById(@Param("id") Integer id);
	
	public void updateCourseById(@Param("course") PlayballCourse course);
}
