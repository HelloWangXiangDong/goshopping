package com.taotao.sso.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisDao;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by XDStation on 2016/8/12 0012.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisDao jedisDao;

    @Value("#{configProperties['Redis_UserSession_Key']}")
    private String Redis_UserSession_Key;

    @Value("#{configProperties['SSO_Session_Expire']}")
    private Integer SSO_Session_Expire;

    /**
     * 检查登陆或者注册数据是否合法
     * @param param
     * @param type
     * @return
     */
    @Override
    public TaotaoResult getUser(String param, Integer type) {
        //type1、2、3 代表username、phone、email
        //param 代表字段
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        switch (type) {
            case 1:
                criteria.andUsernameEqualTo(param);
                break;
            case 2:
                criteria.andPhoneEqualTo(param);
                break;
            case 3:
                criteria.andEmailEqualTo(param);
                break;
        }
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if(tbUsers == null || tbUsers.size()==0){
            return TaotaoResult.ok(true);
        }

        return TaotaoResult.ok(false);
    }

    /**
     * 注册逻辑
     * @param user
     * @return
     */
    @Override
    public TaotaoResult addRegister(TbUser user) {
        //补全pojo
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        //执行插入
        int i = userMapper.insert(user);
        if(i != 0){
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.build(400,"注册失败！");
    }

    /**
     * 登陆逻辑
     * @param username
     * @param password
     * @return
     */
    @Override
    public TaotaoResult getLogin(String username, String password,HttpServletRequest request,
                                 HttpServletResponse response) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        //用户名或者密码输入错误
        if(tbUsers==null && tbUsers.size()==0){
            return TaotaoResult.build(400,"用户名或者密码错误");
        }
        TbUser user = tbUsers.get(0);
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return TaotaoResult.build(400,"用户名或者密码错误");
        }
        //生成随机token
        String token = UUID.randomUUID().toString();
        //保存信息之前把密码清空
        user.setPassword(null);
        //把用户信息写入redis
        jedisDao.set(Redis_UserSession_Key+":"+token, JsonUtils.objectToJson(user));
        jedisDao.expire(Redis_UserSession_Key+":"+token,SSO_Session_Expire);

        //写入cookies
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getToken(String token) {
        if(StringUtils.isEmpty(token)){
            return TaotaoResult.build(400,"session无效！");
        }
        String s = jedisDao.get(Redis_UserSession_Key + ":" + token);
        if(StringUtils.isEmpty(s)){
            return TaotaoResult.build(400,"session已过期！");
        }
        jedisDao.expire(Redis_UserSession_Key+":"+token,SSO_Session_Expire);
        return TaotaoResult.ok(JsonUtils.jsonToPojo(s,TbUser.class));
    }

    @Override
    public TaotaoResult getLogout(String token) {
        if(StringUtils.isEmpty(token)){
            return TaotaoResult.build(400,"session无效！");
        }
        jedisDao.del(Redis_UserSession_Key + ":" + token);
        return TaotaoResult.ok();
    }


}
