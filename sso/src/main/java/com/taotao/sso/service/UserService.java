package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by XDStation on 2016/8/12 0012.
 */
public interface UserService {
    TaotaoResult getUser(String param,Integer type);
    TaotaoResult addRegister(TbUser user);
    TaotaoResult getLogin(String username,String password,HttpServletRequest request,
                          HttpServletResponse response);
    TaotaoResult getToken(String token);
    TaotaoResult getLogout(String token);
}
