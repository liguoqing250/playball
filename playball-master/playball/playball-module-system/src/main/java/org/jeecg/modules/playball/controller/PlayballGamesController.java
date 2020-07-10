package org.jeecg.modules.playball.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.business.model.BusinessFieldInfoPage;
import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.service.IPlayballGamesService;
import org.jeecg.modules.playball.vo.PlayballGamesPage;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@RequestMapping("/cms/playball")
@Slf4j
public class PlayballGamesController extends JeecgController<PlayballGames, IPlayballGamesService> {

    @Autowired
    private IPlayballGamesService gamesService;


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
    public Result<?> queryPageList(PlayballGames playballGames, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
	   
	   IPage<PlayballGamesPage> pageList = gamesService.getAllGames(new Page(pageNo, pageSize));
       return Result.ok(pageList);
    }
   
   @GetMapping(value = "/getGamesInfo")
   public Result<?> getGamesInfoById(HttpServletRequest req) {
	   Integer id = Integer.valueOf(req.getParameter("gamesId"));
	   Result<PlayballGamesPage> result = new Result<>();
		  try {
			  PlayballGamesPage gamesInfo = gamesService.getGamesInfoById(id);
			  result.setSuccess(true);
              result.setMessage("查找成功");
              result.setResult(gamesInfo);
	          return result;
	      } catch (Exception e) {
	      	log.error(e.getMessage(), e);
	          result.setSuccess(false);
	          result.setMessage("查找过程中出现了异常: " + e.getMessage());
	          return result;
	      }
   }
   
    /**
     * 添加
     *
     * @param 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody PlayballGames playballGames, HttpServletRequest request) {
		
    	gamesService.saveGames(playballGames);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param jeecgOrderMainPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> eidt(@RequestBody PlayballGames playballGames) {
    	gamesService.updateGames(playballGames);
        return Result.ok("编辑成功！");
    }
}
