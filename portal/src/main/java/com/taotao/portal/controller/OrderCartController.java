package com.taotao.portal.controller;

import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by XDStation on 2016/8/18 0018.
 */
@Controller
@RequestMapping("/order")
public class OrderCartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrder(Model model, HttpServletRequest request, HttpServletResponse response){
        model.addAttribute("cartList",cartService.getCart(response,request));
        return "order-cart";
    }

    @RequestMapping("/create")
    public String createOrder(Order order,Model model){
        String order1 = orderService.createOrder(order);
        model.addAttribute("orderId",order1);
        model.addAttribute("payment",order.getPayment());
        model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
        return "success";
    }
}
