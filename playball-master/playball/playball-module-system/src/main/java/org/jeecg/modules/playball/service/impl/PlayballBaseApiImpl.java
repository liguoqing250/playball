package org.jeecg.modules.playball.service.impl;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.DataBaseConstant;
import org.jeecg.common.constant.WebsocketConst;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.playball.api.IPlayballBaseAPI;
import org.jeecg.common.playball.vo.*;
import org.jeecg.common.util.*;
import org.jeecg.common.util.oss.OssBootUtil;

import org.jeecg.modules.playball.entity.*;
import org.jeecg.modules.playball.mapper.*;
import org.jeecg.modules.playball.service.IPlayballSportTypeService;
import org.jeecg.modules.playball.service.IPlayballSportsPositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 
 * @Author: 
 * @Date: 
 * @Version:V1.0
 */
@Slf4j
@Service
public class PlayballBaseApiImpl implements IPlayballBaseAPI {
	
	@Autowired
	private IPlayballSportsPositionService positionService;
	//@Resource
	//private SysLogMapper sysLogMapper;
	
	public List<PlayballSportsPositionModel> getPositionListBySportsId(Integer sportsId){
		return positionService.getPositionListBySportsId(sportsId);
	}
	
}