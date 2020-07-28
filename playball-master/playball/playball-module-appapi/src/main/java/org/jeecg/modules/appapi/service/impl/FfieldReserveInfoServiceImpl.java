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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
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
    public List<FieldBookable> queryFieldBookable(String bId,Integer stId) {

        Arena arene=arenaMapper.selectById(bId);
        List<FieldBookable> list=new ArrayList<>();


        for (int i = 0; i < 7; i++) {

            try {
                Map<String,Object> map=new HashMap<>();

                map.put("bId",bId);
                map.put("stId",stId);

                FieldBookable fieldBookable=new FieldBookable();
                Calendar  calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                calendar.add(calendar.DATE,+i); //把日期往后增加一天,整数  往后推,负数往前移动
                map.put("reserveTime",calendar.getTime());
                List<FieldReserveInfo> dataList= fieldReserveInfoMapper.queryFieldBookable(map);
                fieldBookable.setWeekDay(getWeekOfDate(calendar.getTime())+"("+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+")");

                int minHours=Integer.valueOf(arene.getOpenTime().split(":")[0]);
                int maxHours=Integer.valueOf(arene.getCloseTime().split(":")[0]);
                List<Integer> timeArray=new ArrayList();
                List<Integer> mintime=new ArrayList();
                for (int j = minHours; j < maxHours; j++) {

                    timeArray.add(j);
                }
                System.out.println("--------------------------------");
                for (int j = 0; j <timeArray.size() ; j++) {
                    boolean flag=true;
                    for (int k = 0; k <dataList.size() ; k++) {
                        List<String> jsonList= JSONObject.parseArray(dataList.get(k).getFriTiemRanges(),String.class);
                        for (int l = 0; l <jsonList.size() ; l++) {
                            JSONObject obj= JSONObject.parseObject(jsonList.get(l));
                            int hours= Integer.parseInt(  obj.get("startTime").toString().split(" ")[1].split(":")[0]);
                            if(hours==timeArray.get(j)){
                                flag=false;
                                break;
                            }
                        }
                        if(!flag){
                            break;
                        }
                    }
                    if(flag){
                        mintime.add(timeArray.get(j));
                    }
                }

                for (int j = 0; j <mintime.size() ; j++) {
                    System.out.println(mintime.get(j));
                }
                System.out.println("--------------------------------");
                if(mintime.size()>0){
                    fieldBookable.setAvailable(true);
                    String timea="";
                    String timebvalue="";
                    Integer time2=Integer.valueOf(arene.getOpenTime().split(":")[1]);
                    if(mintime.get(0)>10){
                        timea=mintime.get(0).toString();
                    }else{
                        timea="0"+mintime.get(0).toString();
                    }
                    if(time2>10){
                        timebvalue=time2.toString();
                    }else{
                        timebvalue="0"+time2.toString();
                    }
                    fieldBookable.setBookableTime(timea+":"+timebvalue);
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
    public List<FieldReserveInfo> queryFieldReserveInfo(String bid,String reserveTime,Integer stId,Integer fieldType){
        Map<String,Object> map=new HashedMap();
        map.put("bid",bid);
        map.put("stId",stId);
        map.put("reserveTime",reserveTime);

        if(stId==2&&fieldType==2){
            System.out.println("半场");
            Arena arene=arenaMapper.selectById(bid);
            int minHours=Integer.valueOf(arene.getOpenTime().split(":")[0]);
            int maxHours=Integer.valueOf(arene.getCloseTime().split(":")[0]);
            List<FieldReserveInfo> newlist=new ArrayList<>();


            Map<String ,Object> map2=new HashedMap();
            map2.put("business_id",bid);
            map2.put("sports_id",2);
            List<FieldInfo> fieldInfoList=fieldInfoMapper.selectByMap(map2);
            for (int j = 0; j <fieldInfoList.size() ; j++) {
                String value="[";
                FieldReserveInfo fieldReserveInfo=new FieldReserveInfo();
                fieldReserveInfo.setfId(fieldInfoList.get(j).getId());
                map.put("fId",fieldInfoList.get(j).getId());
                for (int i = minHours; i <maxHours ; i++) {
                    map.put("timeValue",i+":"+arene.getOpenTime().split(":")[1]);

                    List<FieldReserveInfo> listdata=fieldReserveInfoMapper.queryFieldReserveInfoHalf(map);
                    if(listdata.size()>0){
                        value+= "{\"startTime\":\"2020-1-1 "+i+":00:00\",\"endTime\":\""+"2020-1-1 "+i+":00:00"+"\"},";
                    }
                }
                if(value.length()>1){
                    value = value.substring(0, value.length() - 1);
                }

                value+="]";
                fieldReserveInfo.setFriTiemRanges(value);
                newlist.add(fieldReserveInfo);
            }

            return newlist;
        }else{
            List<FieldReserveInfo> list=fieldReserveInfoMapper.queryFieldReserveInfo(map);
            return list;
        }

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
