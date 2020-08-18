package org.jeecg.modules.appapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules. appapi.entity.Game;
import org.jeecg.modules.appapi.entity.PlayballNews;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.appapi.entity.PlayballUserComment;
import org.jeecg.modules.appapi.entity.bo.GameBo;
import org.jeecg.modules.appapi.entity.vo.GameVo;
import org.jeecg.modules.appapi.entity.vo.UserCommentVo;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface GameMapper extends BaseMapper<Game> {
    List<Game> queryByFieldId(Integer id);

    List<Game> queryByBid(String id);

    IPage<GameBo> queryPageList(Page<GameVo> page, @Param("vo") GameVo gamevo);

    IPage<GameBo> queryPageListByUid(Page<GameVo> page,  @Param("vo")GameVo gamevo);
    
    //查询全文索引比赛数据
  	IPage<GameBo> findGameFulltext(IPage<Game> page,@Param("fullText") String fullText);

}
