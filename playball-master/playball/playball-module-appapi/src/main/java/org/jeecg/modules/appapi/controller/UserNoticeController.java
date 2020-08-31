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
import org.jeecg.modules.appapi.entity.*;
import org.jeecg.modules.appapi.entity.bo.PlayballUserReplyBo;
import org.jeecg.modules.appapi.entity.bo.ReplyMeData;
import org.jeecg.modules.appapi.entity.vo.AppTeamPlayersVo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserReplyVo;
import org.jeecg.modules.appapi.entity.vo.UserNoticeVo;
import org.jeecg.modules.appapi.mapper.PlayballUserReplyMapper;
import org.jeecg.modules.appapi.mapper.UserNoticeMapper;
import org.jeecg.modules.appapi.service.AppTeamPlayersService;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.jeecg.modules.appapi.service.IUserNoticeService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
@Slf4j
@Api(tags="消息")
@RestController
@RequestMapping("/UserNotice")
public class UserNoticeController extends JeecgController<UserNotice, IUserNoticeService> {
	@Autowired
	private IUserNoticeService userNoticeService;
	@Autowired
	private PlayballUserReplyMapper playballUserReplyMapper;
	@Autowired
	private UserNoticeMapper userNotice;
	@Autowired
	private AppTeamService appTeamService;
	 @Autowired
	 private AppTeamPlayersService appTeamPlayersService;
	/**
	 * 分页列表查询
	 *
	 * @param userNotice
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="消息-分页列表查询", notes="消息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(UserNotice userNotice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		String token=req.getHeader("X-Access-Token");
		AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		userNotice.setReceiverUid(appUsers.getU_id());
		QueryWrapper<UserNotice> queryWrapper = QueryGenerator.initQueryWrapper(userNotice, req.getParameterMap());
		Page<UserNotice> page = new Page<UserNotice>(pageNo, pageSize);

		IPage<UserNotice> pageList = userNoticeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	 //获取回复我的数据(连表查询)
	 @GetMapping("/getReplyMeData")
	 public Result<?> getReplyMeData(PlayballUserReplyVo u_replyVo,HttpServletRequest req){
		 String token=req.getHeader("X-Access-Token");
		 AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		 u_replyVo.setUId(appUsers.getU_id());
		 //分页
		 Page<PlayballUserReply> page = new Page<>(u_replyVo.getPage(), u_replyVo.getLimit());
		 //查询条件
		 IPage<ReplyMeData> selectPage = playballUserReplyMapper.getReplyMeData(page, u_replyVo);
		 return Result.ok(selectPage);
	 }
	 /**
	  * 添加球队公告
	  *
	  * @param userNotice
	  * @return
	  */
	 @ApiOperation(value="消息-添加球队公告", notes="消息-添加球队公告")
	 @PostMapping(value = "/addTeamNotice")
	 public Result<?> addTeamNotice(@RequestBody UserNotice userNotice) {
		 AppTeam appTeam= appTeamService.selectMyTeamInfo();
		 List<AppTeamPlayersVo> appTeamPlayers= appTeamPlayersService.selectByTeamId(appTeam.getTeam_id(),null);
		 for (int i = 0; i < appTeamPlayers.size(); i++) {
			 UserNotice newUserNoteic=userNotice;
			 newUserNoteic.setReceiverUid(appTeamPlayers.get(i).getuId());
			 newUserNoteic.setType(2);
			 userNoticeService.save(newUserNoteic);
		 }
		 return Result.ok("发布成功！");
	 }


	 /**
	  * 获取我未读消息数
	  *
	  * @param userNoticeVo
	  * @return
	  */
	 @ApiOperation(value="消息-通过id查询", notes="消息-通过id查询")
	 @GetMapping(value = "/getCountNotRead")
	 public Result<?> getCountNotRead(UserNoticeVo userNoticeVo, HttpServletRequest req) {
		 String token=req.getHeader("X-Access-Token");
		 AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		 userNoticeVo.setuId(appUsers.getU_id());
		 return Result.ok( userNotice.getCountNotRead(userNoticeVo));
	 }









	/**
	 * 添加
	 *
	 * @param userNotice
	 * @return
	 */
	@ApiOperation(value="消息-添加", notes="消息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody UserNotice userNotice) {
		userNoticeService.save(userNotice);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param userNotice
	 * @return
	 */
	@ApiOperation(value="消息-编辑", notes="消息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody UserNotice userNotice) {
		userNoticeService.updateById(userNotice);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="消息-通过id删除", notes="消息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		userNoticeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="消息-批量删除", notes="消息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userNoticeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="消息-通过id查询", notes="消息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		UserNotice userNotice = userNoticeService.getById(id);
		return Result.ok(userNotice);
	}

}
