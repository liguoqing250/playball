package org.jeecg.modules.appapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.commons.collections.map.HashedMap;
import org.jeecg.modules.appapi.entity.Arena;
import org.jeecg.modules.appapi.entity.FieldBookable;
import org.jeecg.modules.appapi.entity.FieldInfo;
import org.jeecg.modules.appapi.entity.FieldReserveInfo;
import org.jeecg.modules.appapi.mapper.ArenaMapper;
import org.jeecg.modules.appapi.mapper.FieldInfoMapper;
import org.jeecg.modules.appapi.mapper.FieldReserveInfoMapper;
import org.jeecg.modules.appapi.service.IFieldReserveInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 场地预定
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class FfieldReserveInfoServiceImpl extends ServiceImpl<FieldReserveInfoMapper, FieldReserveInfo> implements IFieldReserveInfoService {

    @Autowired
    FieldReserveInfoMapper fieldReserveInfoMapper;

    @Autowired
    ArenaMapper arenaMapper;

    @Autowired
    FieldInfoMapper fieldInfoMapper;

    @Override
    public List<FieldBookable> queryFieldBookable(FieldReserveInfo fieldReserveInfo) {

        Arena arene=arenaMapper.selectById(fieldInfoMapper.selectById(fieldReserveInfo.getfId()).getBusinessId());
        List<FieldBookable> list=new ArrayList<>();


        for (int i = 0; i < 7; i++) {

            try {
                Map<String,Object> map=new HashMap<>();

                map.put("bId",arene.getId());
                map.put("stId",fieldReserveInfo.getStId());

                FieldBookable fieldBookable=new FieldBookable();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar  calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                calendar.add(calendar.DATE,+i); //把日期往后增加一天,整数  往后推,负数往前移动
                map.put("reserveTime",calendar.getTime());
                List<FieldReserveInfo> dataList= fieldReserveInfoMapper.queryFieldBookable(map);
                fieldBookable.setWeekDay(getWeekOfDate(calendar.getTime())+"("+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+")");

                int minHours=Integer.valueOf(arene.getOpenTime().split(":")[0]);
                int maxHours=Integer.valueOf(arene.getCloseTime().split(":")[0]);
                List<Integer> mintime=new ArrayList();
                for (int j = minHours; j < maxHours; j++) {
                    mintime.add(j);
                }
                Iterator<Integer> mintimeit = mintime.iterator();
                    for (int k = 0; k <dataList.size() ; k++) {

                        List<String> jsonList= JSONObject.parseArray(dataList.get(k).getFriTiemRanges(),String.class);

                        for (int l = 0; l <jsonList.size() ; l++) {
                            JSONObject obj= JSONObject.parseObject(jsonList.get(l));

                            int hours= Integer.parseInt(  obj.get("startTime").toString().split(" ")[1].split(":")[0]);
                            while (mintimeit.hasNext()) {
                            if(hours==mintimeit.next()){
                                mintimeit.remove();
                                break;
                            }
                        }
                    }
                }
                List<Integer> newTimeList=new ArrayList<>();
                while(mintimeit.hasNext()){
                    newTimeList.add(mintimeit.next());
                }
                if(newTimeList.size()>0){
                    fieldBookable.setAvailable(true);
                    fieldBookable.setBookableTime(newTimeList.get(0)+":"+Integer.valueOf(arene.getOpenTime().split(":")[1]));
                }else{
                    fieldBookable.setBookableTime("无");
                    fieldBookable.setAvailable(false);
                }

                list.add(fieldBookable);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return list;
    }
    public List<FieldReserveInfo> queryFieldReserveInfo(String bid,Date reserveTime,Integer stId){
        Map<String,Object> map=new HashedMap();
        map.put("bid",bid);
        map.put("stId",stId);
        if(reserveTime!=null){
            map.put("reserveTime",reserveTime);
        }
        return fieldReserveInfoMapper.queryFieldReserveInfo(map);
    }

    public String getWeekOfDate(Date date) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
