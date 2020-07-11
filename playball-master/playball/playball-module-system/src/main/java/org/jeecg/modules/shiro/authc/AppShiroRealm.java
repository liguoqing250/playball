package org.jeecg.modules.shiro.authc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class AppShiroRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("===============Shiro权限认证开始======================");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("appUsers");
        info.setRoles(roleSet);

        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("create");
        permissionSet.add("update");
        permissionSet.add("delete");
        permissionSet.add("select");
        info.addStringPermissions(permissionSet);
        log.info("===============Shiro权限认证成功==============");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        if (token == null) {
            log.info("————————身份认证失败——————————IP地址:  "+ oConvertUtils.getIpAddrByRequest(SpringContextUtils.getHttpServletRequest()));
            throw new AuthenticationException("token为空!");
        }
        // 校验token有效性
        AppUsers appUsers = this.checkUserTokenIsEffect(token);
        return new SimpleAuthenticationInfo(appUsers, token, getName());
    }
    /**
     * 校验token的有效性
     *
     * @param token
     */
    public AppUsers checkUserTokenIsEffect(String token) throws AuthenticationException {
        // 解密获得username，用于和数据库进行对比
    	String loginType = JwtUtil.getLoginType(token);
        String userinfo = JwtUtil.getUserInfo(token);
        if (userinfo == null) {
            throw new AuthenticationException("token非法无效!");
        }
        JSONObject jsonObject = JSONObject.parseObject(userinfo);
        AppUsers appUsers=jsonObject.toJavaObject(AppUsers.class);

        // 查询用户信息
        log.debug("———校验token是否有效————checkUserTokenIsEffect——————— "+ token);
        if (appUsers == null) {
            throw new AuthenticationException("用户不存在!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, userinfo, Integer.toString(appUsers.getU_id()),loginType)) {
            throw new AuthenticationException("Token失效，请重新登录!");
        }

        return appUsers;
    }
    /**
     * JWTToken刷新生命周期 （实现： 用户在线操作不掉线功能）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)，缓存有效期设置为Jwt有效时间的2倍
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 4、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 注意： 前端请求Header中设置Authorization保持不变，校验有效性以缓存中的token为准。
     *       用户过期时间 = Jwt有效时间 * 2。
     *
     * @param userInfo
     * @param userid
     * @return
     */
    public boolean jwtTokenRefresh(String token, String userInfo, String userid,String loginType) {
        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (oConvertUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.verify(cacheToken, userInfo, userid)) {
                String newAuthorization = JwtUtil.sign(userInfo, userid,loginType);
                // 设置超时时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME *2 / 1000);
                log.info("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— "+ token);
            }
            return true;
        }
        return false;
    }
    /**
     * 清除当前用户的权限认证缓存
     *
     * @param principals 权限信息
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
