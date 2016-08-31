package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by XDStation on 2016/8/15 0015.
 */
public interface CartService {
    List<Cart> getCart(HttpServletResponse response, HttpServletRequest request);

    void deleteCart(HttpServletRequest request,HttpServletResponse response,Long id);

    void updateCartNum(Long cid,int num, HttpServletRequest request,HttpServletResponse response);

    TaotaoResult addCart(Long cid,int num, HttpServletRequest request,HttpServletResponse response);
}
