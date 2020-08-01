package org.jeecg.modules.playball.service;

import java.util.List;

import org.jeecg.modules.playball.entity.PlayballEnroll;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.common.playball.vo.PlayballPlayerModel;
import org.jeecg.modules.playball.vo.PlayballEnrollPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 报名球队管理
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
public interface IPlayballEnrollService extends IService<PlayballEnroll> {
	public IPage<PlayballEnrollPage> queryEnrollList(IPage page, PlayballEnrollPage enrollPage);
	

	public List<PlayballTeam> getEnrollTeamByGamesId(Integer gamesId);
	
	public List<PlayballPlayerModel>  getEnrollTeamPlayerById(Integer id);
}
