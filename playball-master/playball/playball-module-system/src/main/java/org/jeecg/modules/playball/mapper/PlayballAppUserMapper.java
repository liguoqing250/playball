package org.jeecg.modules.playball.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballAppUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 会员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface PlayballAppUserMapper extends BaseMapper<PlayballAppUser> {
	
	public List<PlayballAppUser> getAppUserList(IPage page, @Param("playballAppUser")PlayballAppUser playballAppUser);
	
	public Integer getAppUserListTotal(@Param("playballAppUser")PlayballAppUser playballAppUser);
}
