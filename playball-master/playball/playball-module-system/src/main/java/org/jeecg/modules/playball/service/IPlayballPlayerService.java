package org.jeecg.modules.playball.service;

import org.jeecg.common.playball.entity.PlayballPlayer;
import org.jeecg.common.playball.vo.PlayballPlayerModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 队员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IPlayballPlayerService extends IService<PlayballPlayer> {
	
	public IPage<PlayballPlayerModel> queryList(IPage page, PlayballPlayerModel playerModel);
}
