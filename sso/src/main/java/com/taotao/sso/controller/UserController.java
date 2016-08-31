package com.taotao.sso.controller;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by XDStation on 2016/8/12 0012.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查数据是否可用
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkUser(@PathVariable String param,@PathVariable Integer type,String callback) throws UnsupportedEncodingException {
        TaotaoResult taotaoResult = null;
        //type可选参数
        List<Integer> types = new ArrayList<>(Arrays.asList(1,2,3));
        //参数有效性校验
        if(StringUtils.isEmpty(param)){
            taotaoResult = TaotaoResult.build(400,"校验内容不能为空！");
        }
        if(type == null){
            taotaoResult = TaotaoResult.build(400,"校验内容不能为空！");
        }
        if(!types.contains(type)){
            taotaoResult = TaotaoResult.build(400,"校验内容不能为空！");
        }
        if(taotaoResult != null){
            if(callback != null) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }else{
                return taotaoResult;
            }
        }
        try {
            taotaoResult = userService.getUser(param,type);
        } catch (Exception e) {
            e.printStackTrace();
            taotaoResult =  TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        if(callback != null){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(taotaoResult);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }else{
            return taotaoResult;
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        if(user.getUsername() == null || user.getPassword() == null){
            return TaotaoResult.build(400,"注册失败. 请校验数据后请再提交数据");
        }
        try {
            TaotaoResult taotaoResult = userService.addRegister(user);
            return taotaoResult;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(400,ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request,
                              HttpServletResponse response){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return TaotaoResult.build(400,"用户名或者密码不能为空！");
        }
        try {
            return userService.getLogin(username,password,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(400,ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用token老查询redis中的数据
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getToken(@PathVariable String token,String callback){
        TaotaoResult result = null;

        try {
            result = userService.getToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        //判断jsonp参数是否存在
        if(!StringUtils.isEmpty(callback)){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }

    @RequestMapping("/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token,String callback){
        TaotaoResult result = null;
        try {
            result = userService.getLogout(token);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        //判断回调函数是否存在
        if(!StringUtils.isEmpty(callback)){
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;

    }

}
