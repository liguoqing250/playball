package org.jeecg.modules.appapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.appapi.entity.AbilityValue;
import org.jeecg.modules.appapi.entity.ScheduleUserdata;
import org.jeecg.modules.appapi.entity.bo.TeamRankBo;
import org.jeecg.modules.appapi.entity.vo.PlayerScoreInfo;
import org.jeecg.modules.appapi.service.IAbilityValueService;
import org.jeecg.modules.appapi.service.IScheduleUserdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
* @Description: 运动能力值
* @Author: jeecg-boot
* @Date:   2020-07-29
* @Version: V1.0
*/
@Slf4j
@Api(tags="球员数据")
@RestController
@RequestMapping("/PlayerInfo")
public class PlayerInfoController {
   @Autowired
   private IScheduleUserdataService scheduleUserdataService;

    /**
     * 列表查询
     *
     * @param tpId
     * @return
     */
    @ApiOperation(value="赛程球员数据-分页列表查询", notes="赛程球员数据-分页列表查询")
    @PostMapping(value = "/queryPlayerInfoByTpId")
    public Result<?> queryPlayerInfoByTpId(@RequestParam  Integer tpId) {
        List<PlayerScoreInfo> list=scheduleUserdataService.queryPlayerInfoByTpId(tpId);
        return Result.ok(list);
    }
    /**
     * 列表全部比分
     *
     * @return
     */
    @ApiOperation(value="赛程球员数据-分页列表查询", notes="赛程球员数据-分页列表查询")
    @PostMapping(value = "/queryAllData")
    public Result<?> queryPlayerScoreByUid() {
        List<ScheduleUserdata> list=scheduleUserdataService.queryAll();
        return Result.ok(list);
    }
    /**
     * 列表全部球队比分
     *
     * @return
     */
    @ApiOperation(value="赛程球员数据-分页列表查询", notes="赛程球员数据-分页列表查询")
    @PostMapping(value = "/queryAllAsTeam")
    public Result<?> queryAllAsTeam() {
        List<TeamRankBo> list=scheduleUserdataService.queryAllAsTeam();
        return Result.ok(list);
    }
}
