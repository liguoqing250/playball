package org.jeecg.modules.appapi.service.impl;

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
        Map<String,Object> map=new HashMap<>();
        List<FieldReserveInfo> dataList= fieldReserveInfoMapper.selectByMap(map);
        Date time=new Date();
        for (int i = 0; i < 7; i++) {

            try {
                FieldBookable fieldBookable=new FieldBookable();
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");//注意月份是MM
                Calendar  calendar = new GregorianCalendar();
                calendar.setTime(time);
                calendar.add(calendar.DATE,i); //把日期往后增加一天,整数  往后推,负数往前移动
                time=calendar.getTime();
                fieldBookable.setWeekDay(getWeekOfDate(time)+"("+time.getMonth()+"/"+dayFormat.format(time)+")");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");//注意月份是MM
                Date OpenTime = simpleDateFormat.parse(arene.getOpenTime());
                fieldBookable.setBookableTime(OpenTime.getHours()+":"+OpenTime.getMinutes());
                list.add(fieldBookable);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return list;
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
