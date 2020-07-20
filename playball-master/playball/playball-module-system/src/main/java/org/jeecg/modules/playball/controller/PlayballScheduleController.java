package org.jeecg.modules.playball.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.service.IPlayballScheduleService;
import org.jeecg.modules.playball.service.IPlayballGameService;
import org.jeecg.modules.playball.service.IPlayballTeamService;
import org.jeecg.modules.playball.service.IPlayballEnrollService;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.jeecg.modules.playball.util.GameUtils;

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
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Slf4j
@Api(tags="赛程表")
@RestController
@RequestMapping("/playball/playballSchedule")
public class PlayballScheduleController extends JeecgController<PlayballSchedule, IPlayballScheduleService> {
	@Autowired
	private IPlayballScheduleService playballScheduleService;
	
	@Autowired
	private IPlayballGameService gameService;

	@Autowired
	private IPlayballTeamService teamService;
	
	@Autowired
	private IPlayballEnrollService enrollService;
	
	@Autowired
	private IPlayballScheduleService scheduleService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballSchedule
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "赛程表-分页列表查询")
	@ApiOperation(value="赛程表-分页列表查询", notes="赛程表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballScheduleInfoPage playballScheduleInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballSchedule> queryWrapper = QueryGenerator.initQueryWrapper(playballSchedule, req.getParameterMap());
		Page<PlayballSchedule> page = new Page<PlayballSchedule>(pageNo, pageSize);
		IPage<PlayballSchedule> pageList = playballScheduleService.page(page, queryWrapper);*/
		
		IPage<PlayballScheduleInfoPage> pageList = playballScheduleService.querySchedulePageList(new Page(pageNo, pageSize), playballScheduleInfo);
	       
		return Result.ok(pageList);
	}
	
	@GetMapping(value="/getOutMatchList")
	public Result<?> getOutMatchList(HttpServletRequest req) {
		Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
		PlayballGame gameInfo = gameService.getById(gamesId);
		Integer stage = gameInfo.getStage();
		
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("gameInfo", gameInfo);
		//Result<List<List<PlayballScheduleResultVo>>> result = new Result<>();
		try {
			//获取每个阶段stage的比赛返回
			if(stage != 0) {
				List<List<PlayballScheduleResultVo>> scheduleList  = playballScheduleService.getScheduleListByStage(gamesId,stage);
				if (scheduleList != null && scheduleList.size() > 0) {
					jsonObj.put("scheduleList", scheduleList);
					result.setSuccess(true);
					result.setMessage("查找成功");
		        	result.setResult(jsonObj);
				} else {
					result.setSuccess(false);
					result.setMessage("查找失败");
				}
			}else {
				result.setSuccess(true);
				result.setMessage("查找成功");
	        	result.setResult(jsonObj);
			}
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("查找过程中出现了异常: " + e.getMessage());
			return result;
		}
	}
	
   @GetMapping(value = "/createOutMacth")
   public Result<?> getCreateOutMacth(HttpServletRequest req) {
	  Integer gameId = Integer.valueOf(req.getParameter("gamesId"));
	  String  nextStage = req.getParameter("nextStage");
	  boolean bFix = true;
	  
	  Result<JSONObject> result = new Result<JSONObject>();
	  JSONObject jsonObj = new JSONObject();
	  jsonObj.put("finished", 0);
	  //Result<List<PlayballScheduleResultVo>> result = new Result<>();
	  List<PlayballScheduleResultVo> scheduleList = new ArrayList<PlayballScheduleResultVo>();
	  try {
          List<PlayballTeam> teamList = new ArrayList<PlayballTeam>();
          if(nextStage.equals("false")) {
        	  teamList = enrollService.getEnrollTeamByGamesId(gameId);
          }else {
        	  //根据赛事id和stage获取stage阶段获胜队伍
        	  //teamList = scheduleService.getWinTeamByGameId(gameId);
        	  List<PlayballSchedule> schList = scheduleService.getScheduleListByGameId(gameId);
        	  for(int i=0; i < schList.size();i++) {
        		  PlayballTeam team = new PlayballTeam();
        		  if(schList.get(i).getGameResult() == 1) {
        			  team = teamService.getById(schList.get(i).getTeamId());
        		  }else {
        			  team = teamService.getById(schList.get(i).getOpponentId());
        		  }
        		  teamList.add(team);
        	  }
        	  //剩最后4只队伍，交叉赛，决出123名
        	  if(teamList.size()<=2) {
        		  bFix = false;
        		  for(int i=0; i < schList.size();i++) {
            		  PlayballTeam team = new PlayballTeam();
            		  if(schList.get(i).getGameResult() == 1) {
            			  team = teamService.getById(schList.get(i).getOpponentId());
            		  }else {
            			  team = teamService.getById(schList.get(i).getTeamId());
            		  }
            		  teamList.add(team);
            	  }
        	  }
          }
          
          if (teamList != null && teamList.size() > 0) {
        	  //创建一个新集合
        	  List<PlayballTeam> newList = new ArrayList<PlayballTeam>();
        	  if(bFix) {
        		  newList = GameUtils.disorganizeList(teamList);
        	  }else {
        		  newList = teamList;
        		  jsonObj.put("finished", 1);
        	  }
        	  //两两分组
        	  List<List<PlayballTeam>> matchList = GameUtils.fixedGrouping(newList, 2);
              
              for(int i=0; i<matchList.size(); i++) {
            	  PlayballScheduleResultVo scheduleResultVo = new PlayballScheduleResultVo();
            	  PlayballSchedule  gameSchedule = new PlayballSchedule();
            	  gameSchedule.setGamesId(gameId);
            	  scheduleResultVo.setGameSchedule(gameSchedule);
            	  if(matchList.get(i).size() == 2) {
            		  scheduleResultVo.setTeam(matchList.get(i).get(0));
            		  scheduleResultVo.setTopponent(matchList.get(i).get(1));
            	  }else {
      					//轮空球队
            		  scheduleResultVo.setTeam(matchList.get(i).get(0));
            	  }
            	  scheduleList.add(scheduleResultVo);
              }
              jsonObj.put("schedule", scheduleList);
              result.setSuccess(true);
              result.setMessage("查找成功");
              result.setResult(jsonObj);
          	} else {
              result.setSuccess(false);
              result.setMessage("查找失败");
          }
          return result;
      } catch (Exception e) {
      	log.error(e.getMessage(), e);
          result.setSuccess(false);
          result.setMessage(e.getMessage());
          return result;
      }
   }
   
   /**
    * 添加淘汰赛程
    * */
   @RequestMapping(value = "/addOutMacth", method = RequestMethod.POST)
   public Result<?> addMatchInfo(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
	   log.info("PlayballScheduleResultVo-------获取PlayballScheduleResultVo="+jsonObject);
	   Integer gamesId = jsonObject.getIntValue("gamesId");
	   Integer finished = jsonObject.getIntValue("finished");
	   //String str = jsonObject.getString("matchList");
	   //List<PlayballScheduleResultVo>  matchList1 = jsonObject.getObject("matchList", List.class);
	   List<PlayballScheduleResultVo> matchList = JSON.parseArray(jsonObject.getJSONArray("matchList").toJSONString(),PlayballScheduleResultVo.class);
	   
	   PlayballGame gameInfo =gameService.getById(gamesId);
	   Integer stage = gameInfo.getStage()+1;
	   gameInfo.setStage(stage);
	   if(finished == 1) {
		   gameInfo.setFinished(1);//更新赛事状态，表示赛事所有比赛结束啦
	   }
	   for(int i=0; i<matchList.size(); i++) {
			//获取要比赛的两只球队
			PlayballSchedule gameSchedule = matchList.get(i).getGameSchedule();
			gameSchedule.setGamesId(gameInfo.getId());
			gameSchedule.setStage(stage);
			gameSchedule.setTeamId(matchList.get(i).getTeam().getTeamId());
			if(matchList.get(i).getTopponent() != null) {
				gameSchedule.setOpponentId(matchList.get(i).getTopponent().getTeamId());
			}else {
				//如果为空，轮空队伍3:0获胜
				gameSchedule.setEnterBall(3);
				gameSchedule.setGameResult(1);
				gameSchedule.setGameStatus(1);
			}
			
			scheduleService.save(gameSchedule);
		}
	   gameService.updateById(gameInfo);
	   
	   return Result.ok("添加成功！");
   }
   
   /**循环赛-------------*/
   /**
    * 
    * 创建循环赛
    * */
   @GetMapping(value = "/createLoopMacth")
   public Result<?> getCreateLoopMacth(HttpServletRequest req) {
	   Integer gameId = Integer.valueOf(req.getParameter("gamesId"));
	   
	   List<PlayballTeam> teamList = enrollService.getEnrollTeamByGamesId(gameId);
	   
	   teamList = GameUtils.disorganizeList(teamList);
	   Result<List<List<Map<String, PlayballTeam>>>> result = new Result<List<List<Map<String, PlayballTeam>>>>();
	   List<List<Map<String, PlayballTeam>>> matchList = GameUtils.createGroup(teamList);
	   try{
		   result.setResult(matchList);
           result.success("OK");
       }catch (Exception e){
           e.printStackTrace();
           result.error500("请求失败");
       }
	   return result;
   }
   
   /**
    * 添加循环赛赛程
    * */
   @RequestMapping(value = "/addLoopMacth", method = RequestMethod.POST)
   public Result<?> addLoopMacth(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
	   log.info("addLoopMacth-------获取addLoopMacth="+jsonObject);
	   Integer gamesId = jsonObject.getIntValue("gamesId");
	   Integer finished = jsonObject.getIntValue("finished");
	   
	   Integer stage = 0;
	   List<List<Map<String, PlayballTeam>>> matchList = (List<List<Map<String, PlayballTeam>>>)jsonObject.get("matchList");
	   for(int i=0; i<matchList.size(); i++ ) {
		   stage = i+1;
		   PlayballSchedule schedule = new PlayballSchedule();
		   for(int j=0; j<matchList.get(i).size(); j++) {
			   PlayballTeam team = JSON.parseObject(JSON.toJSONString(matchList.get(i).get(j).get("team")),PlayballTeam.class);
			   PlayballTeam opponent = JSON.parseObject(JSON.toJSONString(matchList.get(i).get(j).get("opponent")),PlayballTeam.class);
			   schedule.setGamesId(gamesId);
			   schedule.setStage(stage);
			   schedule.setTeamId(team.getTeamId());
			   schedule.setOpponentId(opponent.getTeamId());
			   log.info("-------PlayballSchedule="+schedule);
			   scheduleService.save(schedule);
		   }
	   }
	   gameService.updateStageById(stage,gamesId);
	   
	   return Result.ok("添加成功！");
   }
   
   @GetMapping(value="/getLoopMatchList")
	public Result<?> getLoopMatchList(HttpServletRequest req) {
		Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
		PlayballGame gameInfo = gameService.getById(gamesId);
		Integer stage = gameInfo.getStage();
		
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("gameInfo", gameInfo);
		List<List<PlayballScheduleResultVo>> resultList = new ArrayList<List<PlayballScheduleResultVo>>();
		try {
			List<PlayballSchedule> scheduleList  = playballScheduleService.getAllScheduleListByGameId(gamesId);
			for(int j=1; j<=stage; j++) {
				List<PlayballScheduleResultVo> macthList = new ArrayList<PlayballScheduleResultVo>();
				for(int i=0; i < scheduleList.size();i++) {
					if(scheduleList.get(i).getStage() == j) {
						PlayballScheduleResultVo scheduelVo = new PlayballScheduleResultVo();
						scheduelVo.setGameSchedule(scheduleList.get(i));
						scheduelVo.setTeam(teamService.getById(scheduleList.get(i).getTeamId()));
						scheduelVo.setTopponent(teamService.getById(scheduleList.get(i).getOpponentId()));
						macthList.add(scheduelVo);
					}
				}
				resultList.add(macthList);
			}
			
			jsonObj.put("scheduleList", resultList);
			result.setSuccess(true);
			result.setMessage("查找成功");
        	result.setResult(jsonObj);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("查找过程中出现了异常: " + e.getMessage());
			return result;
		}
	}
  
   /**循环赛end*/
   
	/**
	 * 添加
	 *
	 * @param playballSchedule
	 * @return
	 */
	@AutoLog(value = "赛程表-添加")
	@ApiOperation(value="赛程表-添加", notes="赛程表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballSchedule playballSchedule) {
		//playballScheduleService.save(playballSchedule);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballSchedule
	 * @return
	 */
	@AutoLog(value = "赛程表-编辑")
	@ApiOperation(value="赛程表-编辑", notes="赛程表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballSchedule playballSchedule) {
		//playballScheduleService.updateById(playballSchedule);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "赛程表-通过id删除")
	@ApiOperation(value="赛程表-通过id删除", notes="赛程表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		//playballScheduleService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "赛程表-批量删除")
	@ApiOperation(value="赛程表-批量删除", notes="赛程表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		//this.playballScheduleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "赛程表-通过id查询")
	@ApiOperation(value="赛程表-通过id查询", notes="赛程表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballSchedule playballSchedule = playballScheduleService.getById(id);
		return Result.ok(playballSchedule);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballSchedule
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballSchedule playballSchedule) {
      return super.exportXls(request, playballSchedule, PlayballSchedule.class, "赛程表");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, PlayballSchedule.class);
  }

}
