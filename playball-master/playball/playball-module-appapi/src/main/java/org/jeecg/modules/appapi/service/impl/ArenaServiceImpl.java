package org.jeecg.modules.appapi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.map.HashedMap;
import org.jeecg.modules.appapi.entity.Arena;
import org.jeecg.modules.appapi.mapper.ArenaMapper;
import org.jeecg.modules.appapi.service.IArenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @Description: 商家表
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Service
public class ArenaServiceImpl extends ServiceImpl<ArenaMapper, Arena> implements IArenaService {
    @Autowired
    ArenaMapper arenaMapper;

    @Override
    public Page<Arena> queryPageList(Page<Arena> page, Map<String, String[]> parameterMap) {
        Map<String,Object> map=new HashedMap();
        for(String key:parameterMap.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
            String[] value=parameterMap.get(key);
            map.put(key,value[0]);
            System.out.print(key);
            System.out.println(value[0]);
        }
        int pageNo= Integer.valueOf(map.get("pageNo").toString());
        int pageSize= Integer.valueOf(map.get("pageSize").toString());
        map.put("offSet",(pageNo-1)*pageSize);
        map.put("limit",pageSize);
        return page.setRecords( arenaMapper.queryPageList(map));
    }
}
