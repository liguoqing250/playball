package org.jeecg.modules.playball.service;

import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.common.playball.vo.PlayballTeamModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 球队
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
public interface IPlayballTeamService extends IService<PlayballTeam> {

	public IPage<PlayballTeamModel> queryTeamList(IPage page, PlayballTeamModel playballTeam);
}
