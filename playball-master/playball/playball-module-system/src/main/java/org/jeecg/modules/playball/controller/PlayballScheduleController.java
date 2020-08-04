package org.jeecg.modules.playball.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import org.jeecg.common.playball.entity.PlayballTeam;
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
import org.springframework.beans.BeanUtils;
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
		PlayballGame gameInfo = gameService.getById(Integer.valueOf(req.getParameter("gamesId")));
		
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("gameInfo", gameInfo);
		try {
			List<List<PlayballScheduleInfoPage>> matchList = playballScheduleService.getMacthByGameId(Integer.valueOf(req.getParameter("gamesId")));
			jsonObj.put("matchList", matchList);
			
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
	
   @GetMapping(value = "/createOutMacth")
   public Result<?> getCreateOutMacth(HttpServletRequest req) {
	  Result<JSONObject> result = new Result<JSONObject>();
	  try {
		  JSONObject jsonObj = scheduleService.createMacthList(Integer.valueOf(req.getParameter("gamesId")), req.getParameter("nextStage"));
		
		  result.setSuccess(true);
          result.setMessage("查找成功");
          result.setResult(jsonObj);
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
	   
	   scheduleService.addMatch(JSON.parseArray(jsonObject.getJSONArray("matchList").toJSONString(),PlayballScheduleInfoPage.class),
			   jsonObject.getIntValue("gamesId"), jsonObject.getIntValue("finished"));
	  
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
	   Result<List<List<PlayballScheduleInfoPage>>> result = new Result<List<List<PlayballScheduleInfoPage>>>();
	   try {
		   List<PlayballTeam> teamList = enrollService.getEnrollTeamByGamesId(gameId);
		   //如果队伍为单数，加一虚拟队伍进行分组
		   if(teamList.size()%2 == 1) {
			   PlayballTeam team = new PlayballTeam();
			   team.setTName("yuzhi");
			   teamList.add(team);
		   }
		   teamList = GameUtils.disorganizeList(teamList);
		   
		   Integer stage = 0;
		   List<List<PlayballScheduleInfoPage>> matchList = new ArrayList<List<PlayballScheduleInfoPage>>();
		   List<List<Map<String, PlayballTeam>>> list = GameUtils.createGroup(teamList);
		   for(int i=0; i<list.size(); i++ ) {
			   stage = i+1;
			   List<PlayballScheduleInfoPage> tempList = new ArrayList<PlayballScheduleInfoPage>();
			   for(int j=0; j<list.get(i).size(); j++) {
				   PlayballScheduleInfoPage scheduleInfo = new PlayballScheduleInfoPage();
				   scheduleInfo.setStage(stage);
				   scheduleInfo.setGamesId(gameId);
				   if(list.get(i).get(j).get("team").getTeamId() != null){
					   scheduleInfo.setTeamId(list.get(i).get(j).get("team").getTeamId());
					   scheduleInfo.setTeamName(list.get(i).get(j).get("team").getTName());
					   if(list.get(i).get(j).get("opponent").getTeamId() != null) {
						   scheduleInfo.setOpponentId(list.get(i).get(j).get("opponent").getTeamId());
						   scheduleInfo.setOpponentName(list.get(i).get(j).get("opponent").getTName());
					   }
				   }else{
					   scheduleInfo.setTeamId(list.get(i).get(j).get("opponent").getTeamId());
					   scheduleInfo.setTeamName(list.get(i).get(j).get("opponent").getTName());
				   }
				   
				   tempList.add(scheduleInfo);
			   }
			   matchList.add(tempList);
		   }
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
	   Integer gameId = jsonObject.getIntValue("gamesId");
	   Integer finished = jsonObject.getIntValue("finished");
	   List<List<PlayballScheduleInfoPage>> matchList = (List<List<PlayballScheduleInfoPage>>)jsonObject.get("matchList");
	   scheduleService.addLoopMatch(matchList,gameId, finished);
	   
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
    * 
    * 小组赛+淘汰赛
    * */
   @GetMapping(value = "/createGroupMacth")
   public Result<?> getCreateGroupMacth(HttpServletRequest req) {
	   Integer gameId = Integer.valueOf(req.getParameter("gamesId"));
	   Integer groupNum = Integer.valueOf(req.getParameter("groupNum"));
	   
	   List<PlayballTeam> teamList = enrollService.getEnrollTeamByGamesId(gameId);
	   
	   teamList = GameUtils.disorganizeList(teamList);
	   Result<List<List<PlayballTeam>>> result = new Result<List<List<PlayballTeam>>>();
	   List<List<PlayballTeam>> matchList = GameUtils.grouping(teamList,groupNum);
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
    * 添加小组赛内循环赛赛程
    * */
   @RequestMapping(value = "/addGroupMacth", method = RequestMethod.POST)
   public Result<?> addGroupMacth(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
	   log.info("addLoopMacth-------获取addLoopMacth="+jsonObject);
	   Integer gamesId = jsonObject.getIntValue("gamesId");
	   PlayballGame gameInfo = gameService.getById(gamesId);
	   Integer stage = gameInfo.getStage()+1;
	   gameInfo.setStage(stage);
		
	   Result<JSONObject> result = new Result<JSONObject>();
	   JSONObject jsonObjResult = new JSONObject();
	   jsonObjResult.put("gameInfo", gameInfo);
	   
	   List<List<PlayballTeam>> groupList = (List<List<PlayballTeam>>)jsonObject.get("groupList");
	   List<List<PlayballScheduleInfoPage>> groupMatchList = new ArrayList<>();
	   for(int i=0; i<groupList.size();i++) {
		   List<PlayballTeam> teamList = JSON.parseArray(JSON.toJSONString(groupList.get(i)), PlayballTeam.class);
		   //打乱顺序
		   teamList=GameUtils.disorganizeList(teamList);
		   if(teamList.size()%2 == 1) {
			   //循环设置的时候为偶数，所以
			   PlayballTeam team = new PlayballTeam();
			   team.setTName("yuzhi");
			   teamList.add(team);
		   }
		   //获取赛程
		   List<Map<String, PlayballTeam>> matchList = GameUtils.createGroupEx(teamList);
		   List<PlayballScheduleInfoPage> tempList = new ArrayList<PlayballScheduleInfoPage>();
		   for(int j=0; j<matchList.size(); j++) {
			   PlayballScheduleInfoPage  scheduleInfo = new PlayballScheduleInfoPage();
			   if(matchList.get(j).get("team").getTeamId()!=null && matchList.get(j).get("opponent").getTeamId()!=null) {
				   scheduleInfo.setGamesId(gamesId);
				   scheduleInfo.setGamesName(gameInfo.getGamesName());
				   scheduleInfo.setOpponentId(matchList.get(j).get("team").getTeamId());
				   scheduleInfo.setTeamId(matchList.get(j).get("opponent").getTeamId());
				   scheduleInfo.setOpponentName(matchList.get(j).get("opponent").getTName());
				   scheduleInfo.setTeamName(matchList.get(j).get("team").getTName());
				   scheduleInfo.setOpponentImage(matchList.get(i).get("opponent").getTImage());
				   scheduleInfo.setTeamImage(matchList.get(i).get("team").getTImage());
				   scheduleInfo.setGameStatus(2);//2表示未赛
				   scheduleInfo.setGroupId(String.valueOf(i+1));//设置小组标识
				   scheduleInfo.setStage(stage);
				   
				   PlayballSchedule schedule = new PlayballSchedule();
				   BeanUtils.copyProperties(scheduleInfo, schedule);
				   tempList.add(scheduleInfo);
				   scheduleService.save(schedule);
			   }
			   
		   }
		   groupMatchList.add(tempList);
	   }
	   gameService.updateById(gameInfo);
	   jsonObjResult.put("groupMatchList", groupMatchList);
	   result.setResult(jsonObjResult);
       result.success("OK");
       
       return result;
   }
   
   @GetMapping(value="/getGroupMatchList")
	public Result<?> getGroupMatchList(HttpServletRequest req) {
		Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
		PlayballGame gameInfo = gameService.getById(gamesId);
		Integer stage = gameInfo.getStage();
		
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("gameInfo", gameInfo);
		List<List<PlayballScheduleResultVo>> resultList = new ArrayList<List<PlayballScheduleResultVo>>();
		try {
			List<PlayballScheduleInfoPage> scheduleInfoList = playballScheduleService.getMacthListByGameId(gamesId);
			List<List<PlayballScheduleInfoPage>> matchList = new ArrayList<List<PlayballScheduleInfoPage>>();
			List<List<PlayballScheduleInfoPage>> groupMatchList = new ArrayList<List<PlayballScheduleInfoPage>>();
			for(int j=1; j<=stage; j++) {
				List<PlayballScheduleInfoPage> infoList = new ArrayList<PlayballScheduleInfoPage>();
				if(j==1) {//1表示小组赛阶段（第一阶段）
					for(int i=0; i<scheduleInfoList.size(); i++) {
						if(scheduleInfoList.get(i).getStage()==1) {
							infoList.add(scheduleInfoList.get(i));
						}
					}
					groupMatchList = GameUtils.groupDataByCondition(infoList, new Comparator<PlayballScheduleInfoPage>() {
					     @Override
					     public int compare(PlayballScheduleInfoPage o1, PlayballScheduleInfoPage o2) {
					         return o1.getGroupId().equals(o2.getGroupId()) ? 0 : -1;
					     }
					 });
					//matchList.add(infoList);
				}else {
					for(int i=0; i<scheduleInfoList.size(); i++) {
						if(scheduleInfoList.get(i).getStage()==j) {
							infoList.add(scheduleInfoList.get(i));
						}
					}
					matchList.add(infoList);
				}
				
			}
			
			jsonObj.put("scheduleList", matchList);
			jsonObj.put("groupMatchList", groupMatchList);
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
   
   /**
    * 
    * 小组赛+淘汰赛end
    * */
   
   
   /**
    * 
    * 自定义比赛
    * */
   @GetMapping(value="/getCustomMatchList")
	public Result<?> getCustomMatchList(HttpServletRequest req) {
		PlayballGame gameInfo = gameService.getById(Integer.valueOf(req.getParameter("gameId")));
		
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("gameInfo", gameInfo);
		try {
			List<PlayballScheduleInfoPage> matchList = playballScheduleService.getMacthListByGameId(Integer.valueOf(req.getParameter("gameId")));
			jsonObj.put("matchList", matchList);
			
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
   	
   /**
    * 添加自定义比赛
    * 
    * */
   @RequestMapping(value = "/addCustomMacth", method = RequestMethod.POST)
   public Result<?> addCustomMacth(@RequestBody List<PlayballSchedule> scheduleList, HttpServletRequest request) {
	   log.info("addCustomMacth-------获取addCustomMacth="+scheduleList);
	   //Integer gameId = jsonObject.getIntValue("gamesId");
	   //Integer finished = jsonObject.getIntValue("finished");
	   //List<PlayballSchedule> scheduleList = JSON.parseArray(jsonObject.getString("matchList"), PlayballSchedule.class);
	   //PlayballSchedule schedule = new PlayballSchedule();
	  // BeanUtils.copyProperties(scheduleInfo, schedule);
	   scheduleService.saveBatch(scheduleList);
	   
	   return Result.ok("添加成功！");
   }
   
   /**
    * 
    * 自定义比赛end
    * */
   
   
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
		playballScheduleService.updateById(playballSchedule);
		if(playballSchedule.getGameStatus() == 1) {
			PlayballTeam team = teamService.getById(playballSchedule.getTeamId());
			PlayballTeam opponent = teamService.getById(playballSchedule.getOpponentId());
			//更新球队胜平负场次，可以直接提供接口获取，无需字段存储
			//1胜利，2平，3输
			if(playballSchedule.getGameResult()==1) {
				team.setTWinTotal(team.getTWinTotal()+1);
				teamService.updateById(team);
				opponent.setTLostTotal(opponent.getTLostTotal()+1);
				teamService.updateById(opponent);
			}else if(playballSchedule.getGameResult()==2) {
				team.setTDivideTotal(team.getTDivideTotal()+1);
				teamService.updateById(team);
				opponent.setTDivideTotal(opponent.getTDivideTotal()+1);
				teamService.updateById(opponent);
			}else if(playballSchedule.getGameResult()==3) {
				team.setTLostTotal(team.getTLostTotal()+1);
				teamService.updateById(team);
				opponent.setTWinTotal(opponent.getTWinTotal()+1);
				teamService.updateById(opponent);
			}
		}
			
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
