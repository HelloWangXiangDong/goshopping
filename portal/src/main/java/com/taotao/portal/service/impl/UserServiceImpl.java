package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by XDStation on 2016/8/14 0014.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("#{configProperties['SSO_GetUserByToken_KEY']}")
    private String SSO_GetUserByToken_KEY;
    @Value("#{configProperties['SSO_BASE_URL']}")
    public String SSO_BASE_URL;
    @Value("#{configProperties['SSO_LOGIN_KEY']}")
    public String SSO_LOGIN_KEY;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            String s = HttpClientUtil.doGet(SSO_BASE_URL+SSO_GetUserByToken_KEY + token);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(s, TbUser.class);
            TbUser user  = (TbUser) taotaoResult.getData();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
