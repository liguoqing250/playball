package org.jeecg.modules.appapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.encryption.AesEncryptUtil;
import org.jeecg.common.util.encryption.EncryptedString;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.mapper.AppUsersMapper;
import org.jeecg.modules.appapi.service.AppOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AppOpenServiceImpl implements AppOpenService {
    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
    private static String KEY = EncryptedString.key;
    private static String IV = EncryptedString.iv;

    @Autowired
    AppUsersMapper appUsersMapper;


    @Override
    public Map<String,Object> loginOrRegister(AppUsers appUsers,Boolean usePassworld) {
        Map<String,Object> out=new HashMap<>();
        AppUsers selectAppUsers= appUsersMapper.selectMyRegisterWay(appUsers);
        String defaultPassworld="00000000";
        out.put("success",true);
        //登录
        try {
            if(appUsers.getU_registerWay()!=1){
                //第三方登录或者注册
                if(selectAppUsers!=null){
                    out.put("msg","登录成功");
                    out.put("appUsers",selectAppUsers);
                }else{
                    appUsers.setU_passworld(AesEncryptUtil.encrypt(defaultPassworld));
                    appUsersMapper.insert(appUsers);
                    out.put("appUsers",appUsers);
                    out.put("msg","注册成功");
                }

            }else{
                if(selectAppUsers!=null){
                    if(usePassworld){
                        //密码解密
                        String passworld=AesEncryptUtil.desEncrypt(selectAppUsers.getU_passworld().replaceAll("%2B", "\\+")).trim();//密码解密
                        if(passworld.equals(appUsers.getU_passworld())){
                            out.put("msg","登录成功");
                            out.put("appUsers",selectAppUsers);
                        }else{
                            out.put("success",false);
                            out.put("msg","登录失败密码错误");
                        }
                    }else{
                        out.put("appUsers",selectAppUsers);
                        out.put("msg","登录成功");
                    }
                }else{
                    if(usePassworld){
                        out.put("success",false);
                        out.put("msg","账户不存在");
                    }else{
                        //密码加密
                        appUsers.setU_passworld(AesEncryptUtil.encrypt(defaultPassworld));
                        appUsersMapper.insert(appUsers);
                        out.put("appUsers",appUsers);
                        out.put("msg","注册成功");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
