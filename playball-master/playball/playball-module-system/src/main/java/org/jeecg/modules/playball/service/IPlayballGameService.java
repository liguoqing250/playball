package org.jeecg.modules.playball.service;

import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.vo.PlayballGamesPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
/**
 * @Description: 赛事信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
public interface IPlayballGameService extends IService<PlayballGame> {
	public IPage<PlayballGamesPage> queryGameList(IPage page);
}
