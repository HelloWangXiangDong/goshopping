package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.Cart;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by XDStation on 2016/8/15 0015.
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId,
                                @RequestParam(defaultValue = "1") Integer num,
                                HttpServletRequest request, HttpServletResponse response)
    {
        TaotaoResult taotaoResult = cartService.addCart(itemId, num, request, response);
        return "cartSuccess";

    }

    @RequestMapping("/cart")
    public String getCart(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        List<Cart> cart = cartService.getCart(response, request);
        model.addAttribute("cartList",cart);
        return "cart";
    }

    @RequestMapping("/upd/{itemId}")
    public String updCart(@PathVariable Long itemId,
                          @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response)
    {
        cartService.updateCartNum(itemId, num, request, response);
        return "cart";

    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,
                             HttpServletRequest request, HttpServletResponse response){
        cartService.deleteCart(request,response,itemId);
        return "redirect:/cart/cart.html";
    }
}
