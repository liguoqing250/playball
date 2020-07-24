package org.jeecg.modules.playball.service;

import org.jeecg.common.playball.entity.PlayballAppUser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 会员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IPlayballAppUserService extends IService<PlayballAppUser> {
	public IPage<PlayballAppUser> quereyList(IPage page, PlayballAppUser playballAppUser);
}
