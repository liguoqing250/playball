package org.jeecg.modules.appapi.controller;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.PlayballCurriculum;
import org.jeecg.modules.appapi.entity.PlayballNews;
import org.jeecg.modules.appapi.entity.PlayballUserCollect;
import org.jeecg.modules.appapi.entity.PlayballUserDynamic;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCollectBo;
import org.jeecg.modules.appapi.entity.vo.PlayballUserCollectVo;
import org.jeecg.modules.appapi.mapper.NewsMapper;
import org.jeecg.modules.appapi.mapper.PlayballCurriculumMapper;
import org.jeecg.modules.appapi.mapper.PlayballUserCollectMapper;
import org.jeecg.modules.appapi.mapper.PlayballUserDynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 用户收藏表 前端控制器
 * </p>
 *
 * @author 予之文化传媒公司
 * @since 2020-08-08
 */
@RestController
@RequestMapping("/UserCollect")
public class PlayballUserCollectController {
	@Autowired
	private PlayballUserCollectMapper mapper; //收藏mapper
	
	@Autowired
	private NewsMapper newsMapper; //资讯 mapper
	
	@Autowired
	private PlayballCurriculumMapper curMapper;// 教程 mapper
	
	@Autowired
	private PlayballUserDynamicMapper dynMapper; //动态 mapper
	
	//查询用户收藏数据
	@GetMapping("getCollection")
	public Result<?> getCollection (PlayballUserCollectVo collVo){
		System.out.println(collVo.getUcoCollectedId() + "---" + collVo.getUcoSort() +"----"+ collVo.getLat()+ collVo.getLon());
		Page<PlayballUserCollect> page = new Page<PlayballUserCollect>(collVo.getPage(), collVo.getLimit());
		IPage<PlayballUserCollectBo> selectPage = mapper.selectUserCollect(page, collVo);
		return Result.ok(selectPage);
	}
	
	//更新收藏数据 修改数据逻辑删除的状态
	@PostMapping("/updateCollection")
	public Result<?> updateCollection(PlayballUserCollect coll){
		PlayballUserCollect entity = mapper.selectIsEntity(coll);
		if(entity==null){
			mapper.insert(coll);
		}else{
			mapper.updateStart(entity.getUcoId());
		}
		updateCollect(coll.getUcoCollectedId(),coll.getUcoSort(),1);
		return Result.ok("用户已收藏");
	}
	
	//删除收藏数据 逻辑删除
	@PostMapping("/deleteCollection")
	public Result<?> deleteCollection(PlayballUserCollect coll){
		PlayballUserCollect entity = mapper.selectIsEntity(coll);
		//用户点击取消收藏 如果是空的不操作
		if(entity!=null){
			updateCollect(coll.getUcoCollectedId(),coll.getUcoSort(),0);
			return Result.ok(mapper.deleteById(entity.getUcoId()));
		}
		return Result.ok(null);
	}
	
	//查询用户是否有收藏
	@PostMapping("/findCollection")
	public Result<?> findCollection(PlayballUserCollect coll){
		PlayballUserCollect entity = mapper.selectIsEntity(coll);
		return Result.ok(entity);
	}
	
	//更新评论数
	public void updateCollect(String id,Integer type,int operation){
		if(type == null){//更新资讯表收藏数据
			return ;
		}else if(type == 1){
			PlayballNews news = new PlayballNews();
			news.setId(Integer.parseInt(id));
			news.setUserColls(operation);
			newsMapper.updateNewsBrowse(news);
		}else if(type == 2){
			PlayballCurriculum curriculum = new PlayballCurriculum();
			curriculum.setCId(Integer.parseInt(id));
			curriculum.setUserColls(operation);
			curMapper.updateCurriculumBrowse(curriculum);
		}else if(type == 3){
			PlayballUserDynamic dynamic = new PlayballUserDynamic();
			dynamic.setUdyId(Integer.parseInt(id));
			dynamic.setUserColls(operation);
			dynMapper.updateDynamicBrowse(dynamic);
		}
	}
}

