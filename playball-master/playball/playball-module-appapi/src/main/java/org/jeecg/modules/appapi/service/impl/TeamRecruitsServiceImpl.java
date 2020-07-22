package org.jeecg.modules.appapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.TeamRecruits;
import org.jeecg.modules.appapi.mapper.TeamRecruitsMapper;
import org.jeecg.modules.appapi.service.TeamRecruitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: 招募信息
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
@Service
public class TeamRecruitsServiceImpl extends ServiceImpl<TeamRecruitsMapper, TeamRecruits> implements TeamRecruitsService {
    @Autowired
    TeamRecruitsMapper teamRecruitsMapper;
    @Override
    public Page<TeamRecruits> selectByPage(Map<String, Object> params) {
        Page<TeamRecruits> page=new Page<>();
        int PageSize=(int)params.get("PageSize");
        page.setRecords(teamRecruitsMapper.selectByPage(params));
        page.setSize(PageSize);
        int count=teamRecruitsMapper.getCount(params);
        Long pageSize=page.getSize();
        int totalPages = count / pageSize.intValue(); //JAVA整数之间的除法运算，默认只返回整数位
        if(count % page.getSize() != 0) totalPages++;
        page.setPages(totalPages);
        page.setTotal(totalPages);

        return page;
    }
}
