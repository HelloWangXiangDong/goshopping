package com.taotao.controller;

import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by XDStation on 2016/7/29 0029.
 */
@Controller
public class ItemParamItemController {
    @Autowired
    private ItemParamItemService itemParamItemService;
    @RequestMapping("/itemParamItem/{itemId}")
    public String showItemParamItem(@PathVariable Long itemId,Model model){
        String data = itemParamItemService.getItemParamItem(itemId);
        model.addAttribute("ItemParam",data);
        return "itemParamItem";
    }
}
