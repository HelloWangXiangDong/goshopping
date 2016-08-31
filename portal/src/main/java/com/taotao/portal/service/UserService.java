package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

/**
 * Created by XDStation on 2016/8/14 0014.
 */
public interface UserService {
    TbUser getUserByToken(String token);
}
