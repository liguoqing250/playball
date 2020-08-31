package org.jeecg.modules.appapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.encryption.AesEncryptUtil;
import org.jeecg.common.util.encryption.EncryptedString;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.PlayballUsersBalance;
import org.jeecg.modules.appapi.mapper.AppUsersMapper;
import org.jeecg.modules.appapi.mapper.PlayballUsersBalanceMapper;
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
    private RedisUtil redisUtil;
    @Autowired
    AppUsersMapper appUsersMapper;
    @Autowired
    private PlayballUsersBalanceMapper playballUsersBalanceMapper;

    @Override
    public Map<String,Object> loginOrRegister(AppUsers appUsers,Boolean usePassworld,String json) {
        Map<String,Object> out=new HashMap<>();
        AppUsers selectAppUsers= appUsersMapper.selectMyRegisterWay(appUsers);
        String defaultPassworld="00000000";
        out.put("success",true);
        //登录
        try {
            if(appUsers.getU_registerWay()!=1){

                //第三方登录或者注册
                if(selectAppUsers!=null){
                	appUsers.setU_id(selectAppUsers.getU_id());
                	appUsersMapper.update(appUsers);//登录成功后更新数据
                    out.put("msg","登录成功");
                    out.put("appUsers",selectAppUsers);
                }else{
                    if(appUsers.getU_registerWay()==4){
                        appUsers.setU_name("新用户");
                        appUsers.setU_nickName("新用户");
                        appUsers.setU_sex(1);
                    }else{
                        appUsers.setU_name(appUsers.getU_nickName());
                    }

                    appUsers.setU_passworld(AesEncryptUtil.encrypt(defaultPassworld));
                    appUsers.setU_headImage("https://playball.oss-cn-beijing.aliyuncs.com/upload/1597919073-IMG_0368_1597919087194.PNG");
                    System.out.println(appUsers.toString());
                    appUsersMapper.insert(appUsers);
                    PlayballUsersBalance playballUsersBalance=new PlayballUsersBalance();
                    playballUsersBalance.setUbUserId(appUsers.getU_id());

                    playballUsersBalanceMapper.insert(playballUsersBalance);
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
                        //短信登录
                        JSONObject jsonObject = JSONObject.parseObject(json);
                        if(redisUtil.hasKey(appUsers.getU_phoneNumber())){
                            String code=Integer.toString((int)redisUtil.get(appUsers.getU_phoneNumber()));

                            if(jsonObject.get("code").equals(code)){
                                out.put("appUsers",selectAppUsers);
                                out.put("msg","登录成功");
                            }else{
                                out.put("success",false);
                                out.put("msg","登录失败验证码错误");
                            }
                        }else{
                            out.put("success",false);
                            out.put("msg","登录失败验证码不存在");
                        }


                    }
                }else{
                    if(usePassworld){
                        out.put("success",false);
                        out.put("msg","账户不存在");
                    }else{
                        JSONObject jsonObject = JSONObject.parseObject(json);
                        if(redisUtil.hasKey(appUsers.getU_phoneNumber())){
                            String code=Integer.toString((int)redisUtil.get(appUsers.getU_phoneNumber()));
                            if(jsonObject.get("code").equals(code)){
                                //密码加密
                                appUsers.setU_passworld(AesEncryptUtil.encrypt(defaultPassworld));
                                appUsers.setU_name("新用户");
                                appUsers.setU_nickName("新用户");
                                appUsers.setU_headImage("https://playball.oss-cn-beijing.aliyuncs.com/upload/1597919073-IMG_0368_1597919087194.PNG");
                                appUsersMapper.insert(appUsers);
                                PlayballUsersBalance playballUsersBalance=new PlayballUsersBalance();
                                playballUsersBalance.setUbUserId(appUsers.getU_id());
                                playballUsersBalanceMapper.insert(playballUsersBalance);
                                out.put("appUsers",appUsers);
                                out.put("msg","注册成功");
                            }else{
                                out.put("success",false);
                                out.put("msg","注册失败验证码错误");
                            }
                        }else{
                            out.put("success",false);
                            out.put("msg","注册失败验证码不存在");
                        }


                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

}
