package org.jeecg.modules.appapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.UserFollow;
import org.jeecg.modules.appapi.mapper.UserFollowMapper;
import org.jeecg.modules.appapi.service.IUserFollowService;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 用户关注表
 * @Author: jeecg-boot
 * @Date:   2020-08-12
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户关注表")
@RestController
@RequestMapping("/UserFollow")
public class UserFollowController extends JeecgController<UserFollow, IUserFollowService> {

	 @Autowired
	 private UserFollowMapper userFollowMapper;

	 /**
	  * 关注或取关
	  *
	  * @param followUId
	  * @return
	  */
	 @ApiOperation(value="用户关注表-关注或取关", notes="用户关注表-关注或取关")
	 @PostMapping(value = "/follow")
	 public Result<?> follow(Integer followUId) {
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		 String token=request.getHeader("X-Access-Token");
		 AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		 UserFollow userFollow=new UserFollow();
		 userFollow.setUId(appUsers.getU_id());
		 userFollow.setFollowUId(followUId);
		 UserFollow data= userFollowMapper.isFllow(userFollow);
		 if(data!=null){
			 //已关注
			 userFollowMapper.deleteById(data.getId());
			 return Result.ok("取关成功!");
		 }else{
			 //未关注
			 userFollowMapper.insert(userFollow);
			 return Result.ok("关注成功!");
		 }

	 }
	 /**
	  * 是否关注
	  *
	  * @param followUId
	  * @return
	  */
	 @ApiOperation(value="用户关注表-是否关注", notes="用户关注表-是否关注")
	 @PostMapping(value = "/isFollow")
	 public Result<?> isFollow(Integer followUId) {
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		 String token=request.getHeader("X-Access-Token");
		 AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		 UserFollow userFollow=new UserFollow();
		 userFollow.setUId(appUsers.getU_id());
		 userFollow.setFollowUId(followUId);
		 if(userFollowMapper.isFllow(userFollow)!=null){
		 	//已关注
			 return Result.ok(true);
		 }else{
		 	//未关注
			 return Result.ok(false);
		 }
	 }

}
