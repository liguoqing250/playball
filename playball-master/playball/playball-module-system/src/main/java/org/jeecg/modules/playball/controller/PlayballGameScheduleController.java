package org.jeecg.modules.playball.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;

import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballGameSchedule;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.jeecg.modules.playball.vo.PlayballScheduleVo;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.jeecg.modules.playball.service.IPlayballGameScheduleService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 
 * @Author: 
 * @Date
 * @Version: V2.0
 */
@RestController
@RequestMapping("/cms/schedule")
@Slf4j
public class PlayballGameScheduleController extends JeecgController<PlayballGameSchedule, IPlayballGameScheduleService> {

    @Autowired
    private IPlayballGameScheduleService gameScheduleService;
    
    /**
     * 分页列表查询
     *
     * @param 
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
   @GetMapping(value = "/list")
    public Result<?> queryPageList(PlayballScheduleInfoPage playballScheduleInfoPage, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
	   
	   IPage<PlayballScheduleInfoPage> pageList = gameScheduleService.getAllSchedule(new Page(pageNo, pageSize));
	   log.info("PlayballScheduleInfoPage"+pageList);
       return Result.ok(pageList);
    }
    
    /**
     * 添加
     *
     * @param 
     * @return
     */
    @RequestMapping(value = "/addMatchInfo", method = RequestMethod.POST)
    public Result<?> addMatchInfo(@RequestBody PlayballScheduleVo playballScheduleVo, HttpServletRequest request) {
    	log.info("playballScheduleVo-------获取playballScheduleVo="+playballScheduleVo);
    	
    	gameScheduleService.addMatchInfo(playballScheduleVo.getGamesInfo(), playballScheduleVo.getMatchList());
        return Result.ok("添加成功！");
    }
    
    /**
     * 
     *
     * @param 
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
   @GetMapping(value = "/scheduleList")
    public Result<?> scheduleList(HttpServletRequest req) {
	   Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
	   Integer stage = Integer.valueOf(req.getParameter("stage"));
	   
	  
	   Result<List<List<PlayballScheduleResultVo>>> result = new Result<>();
		  try {
			  //获取每个阶段stage的比赛返回
			   List<List<PlayballScheduleResultVo>> scheduleList = new ArrayList<List<PlayballScheduleResultVo>>();  
			   if(stage != 0) {
				   scheduleList = gameScheduleService.getScheduleListByStage(gamesId,stage);
			   }
			   
	          if (scheduleList != null && scheduleList.size() > 0) {
	              result.setSuccess(true);
	              result.setMessage("查找成功");
	              log.info("f============="+scheduleList);
	              result.setResult(scheduleList);
	          } else {
	              result.setSuccess(false);
	              result.setMessage("查找失败");
	          }
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
     *
     * @param 
     * @param 
     * @param 
     * @param req
     * @return
     */
   @GetMapping(value = "/enrollteam")
    public Result<?> getSchedule(HttpServletRequest req) {
	   Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
	   Result<List<PlayballTeam>> result = new Result<>();
		  try {
	          List<PlayballTeam> enrollTeamList = gameScheduleService.getEnrollTeamByGamesId(gamesId);
	          log.info("获取enrollTeamList="+enrollTeamList);
	          if (enrollTeamList != null && enrollTeamList.size() > 0) {
	              result.setSuccess(true);
	              result.setMessage("查找成功");
	              result.setResult(enrollTeamList);
	          } else {
	              result.setSuccess(false);
	              result.setMessage("查找失败");
	          }
	          return result;
	      } catch (Exception e) {
	      	log.error(e.getMessage(), e);
	          result.setSuccess(false);
	          result.setMessage("查找过程中出现了异常: " + e.getMessage());
	          return result;
	      }
    }
   
   @GetMapping(value = "/matchinfo")
   public Result<?> getEnrollTeamByGamesId(HttpServletRequest req) {
	  Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
	  log.info("获取gamesId="+req.getParameter("gamesId"));
	  Result<List<List<PlayballTeam>>> result = new Result<>();
	  try {
          List<PlayballTeam> enrollTeamList = gameScheduleService.getEnrollTeamByGamesId(gamesId);
 
          if (enrollTeamList != null && enrollTeamList.size() > 0) {
        	  //创建一个新集合
        	  List<PlayballTeam> newList=new ArrayList<PlayballTeam>(); 		 				
        	  int size=enrollTeamList.size();
		 		//根据list长度确定循环次数
        	  for(int i=0;i<size;i++){
        		  // 创建一个随机数，范围是0到list当前元素个数
        		  int random=(int)(Math.random()*enrollTeamList.size());
        		  //把下标为随机出的数的元素添加到newList
        		  newList.add(enrollTeamList.get(random));
        		  //从list中删除刚才添加到newList中的元素，以避免随机出重复的数
        		  enrollTeamList.remove(random);                 								
        	  }
        	  //两两分组
        	  List<List<PlayballTeam>> matchList = fixedGrouping(newList, 2);
              log.info("获取matchList="+matchList);
              
              result.setSuccess(true);
              result.setMessage("查找成功");
              result.setResult(matchList);
          } else {
              result.setSuccess(false);
              result.setMessage("查找失败");
          }
          return result;
      } catch (Exception e) {
      	log.error(e.getMessage(), e);
          result.setSuccess(false);
          result.setMessage("查找过程中出现了异常: " + e.getMessage());
          return result;
      }
   }
   
   @GetMapping(value = "/nextStageScheduleList")
   public Result<?> getNextStageScheduleList(HttpServletRequest req) {
	   Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
	   Integer stage = Integer.valueOf(req.getParameter("currentStage"));
	  
	   Result<List<List<PlayballTeam>>> result = new Result<>();
	   
	   try {
	          List<PlayballTeam> nextTeamList = gameScheduleService.getNextTeamList(gamesId, stage);
	 
	          if (nextTeamList != null && nextTeamList.size() > 0) {
	        	  //创建一个新集合
	        	  List<PlayballTeam> newList=new ArrayList<PlayballTeam>(); 		 				
	        	  int size=nextTeamList.size();
			 		//根据list长度确定循环次数
	        	  for(int i=0;i<size;i++){
	        		  // 创建一个随机数，范围是0到list当前元素个数
	        		  int random=(int)(Math.random()*nextTeamList.size());
	        		  //把下标为随机出的数的元素添加到newList
	        		  newList.add(nextTeamList.get(random));
	        		  //从list中删除刚才添加到newList中的元素，以避免随机出重复的数
	        		  nextTeamList.remove(random);                 								
	        	  }
	        	  //两两分组
	        	  List<List<PlayballTeam>> nextMacthList = fixedGrouping(newList, 2);
	              log.info("获取matchList="+nextMacthList);
	              
	              result.setSuccess(true);
	              result.setMessage("查找成功");
	              result.setResult(nextMacthList);
	          } else {
	              result.setSuccess(false);
	              result.setMessage("查找失败");
	          }
	          return result;
	      } catch (Exception e) {
	      	log.error(e.getMessage(), e);
	          result.setSuccess(false);
	          result.setMessage("查找过程中出现了异常: " + e.getMessage());
	          return result;
	      }
	   }
   

   /**
    * @param 
    * @return
    */
   @PutMapping(value = "/update")
   public Result<?> updateMacthTime(@RequestBody PlayballGameSchedule playballGameSchedule) {
	   
	   log.info("获取playballGameSchedule="+playballGameSchedule);
	   gameScheduleService.updateById(playballGameSchedule);
       return Result.ok("编辑成功！");
   }
   
   /**
    * @param 
    * @return
    */
  /* @PutMapping(value = "/updateResult")
   public Result<?> updateResult(@RequestBody PlayballGameSchedule playballGameSchedule) {
	   
	   gameScheduleService.updateById(playballGameSchedule);
       return Result.ok("编辑成功！");
   }*/

   //比赛生成，两两比赛
   public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

	   if (null == source || source.size() == 0 || n <= 0)
           return null;
       List<List<T>> result = new ArrayList<List<T>>();

      int sourceSize = source.size();
      int size = (source.size() / n) + 1;
      for (int i = 0; i < size; i++) {
          List<T> subset = new ArrayList<T>();
          for (int j = i * n; j < (i + 1) * n; j++) {
              if (j < sourceSize) {
                  subset.add(source.get(j));
              }
          }
           if(subset.size()>0){
              result.add(subset);
          }
      }
      return result;
      }
}
