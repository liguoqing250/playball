package org.jeecg.common.playball.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.jeecg.common.playball.entity.*;
import org.jeecg.common.playball.vo.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


/**
 * @Description: 
 * @Author: 
 * @Date:
 * @Version:V1.0
 */
public interface IPlayballBaseAPI {
	
	public List<PlayballSportsPositionModel> getPositionListBySportsId(Integer sportsId);
	
	public void addFeedback(PlayballFeedback feedback);
	
}
