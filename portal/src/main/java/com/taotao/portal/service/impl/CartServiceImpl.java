package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.Cart;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDStation on 2016/8/15 0015.
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("#{configProperties['REST_BASE_URL']}")
    private String REST_BASE_URL;
    @Value("#{configProperties['item_baseInfo_url']}")
    private String item_baseInfo_url;
    @Value("#{configProperties['TT_Cart']}")
    private String TT_Cart;

    /**
     * 展示
     * @param response
     * @param request
     * @return
     */
    @Override
    public List<Cart> getCart(HttpServletResponse response, HttpServletRequest request) {
        List<Cart> cartList = getCartList(request);
        return cartList;
    }

    /**
     * 删除购物车的商品
     * @param request
     * @param response
     * @param id
     */
    @Override
    public void deleteCart(HttpServletRequest request,HttpServletResponse response, Long id) {
        List<Cart> cartList = getCartList(request);
        for(Cart c : cartList){
            if(c.getId().equals(id)){
                cartList.remove(c);
                break;
            }
        }
        CookieUtils.setCookie(request,response,TT_Cart,JsonUtils.objectToJson(cartList),true);
    }

    /**
     * 修改商品数量
     * @param cid
     * @param num
     * @param request
     * @param response
     */
    @Override
    public void updateCartNum(Long cid, int num, HttpServletRequest request, HttpServletResponse response) {
        //先取购物车商品列表
        Cart cart = null;
        List<Cart> cartList = getCartList(request);
        for(Cart c : cartList){
            //变数量
            if(c.getId().equals(cid)){
                c.setNum(num);
                cart = c;
                break;
            }
        }
        CookieUtils.setCookie(request,response,TT_Cart,JsonUtils.objectToJson(cartList),true);
    }

    /**
     * 向购物车中添加商品
     * @param cid
     * @param num
     * @param request
     * @param response
     * @return
     */
    @Override
    public TaotaoResult addCart(Long cid, int num,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        //先取购物车商品列表
        Cart cart = null;
        List<Cart> cartList = getCartList(request);
        for(Cart c : cartList){
            //如果这个商品已经存在了，加数量
            if(c.getId().equals(cid)){
                c.setNum(c.getNum()+num);
                cart = c;
                break;
            }
        }
        //如果不存在new一个
        if(cart == null) {
            //通过cid得到item
            String s = HttpClientUtil.doGet(REST_BASE_URL + item_baseInfo_url + cid);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(s, TbItem.class);
            if (taotaoResult.getStatus() == 200) {
                TbItem item = (TbItem) taotaoResult.getData();
                cart = new Cart();
                cart.setId(item.getId());
                cart.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cart.setNum(num);
                cart.setPrice(item.getPrice());
                cart.setTitle(item.getTitle());
                cartList.add(cart);
            }
        }
        //把列表重新写入cookie
        CookieUtils.setCookie(request,response,TT_Cart,JsonUtils.objectToJson(cartList),true);
        return TaotaoResult.ok();
    }

    /**
     * 从cookie中取购物车列表
     */
    private List<Cart> getCartList(HttpServletRequest request){
        //从Cookie中取商品列表
        String s = CookieUtils.getCookieValue(request, TT_Cart, true);
        //如果没有商品列表的话就new 一个
        if(s == null){
            return new ArrayList<>();
        }
        try {
            //把json转换为List
            List<Cart> carts = JsonUtils.jsonToList(s, Cart.class);
            return carts;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
