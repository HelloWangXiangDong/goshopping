package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by XDStation on 2016/8/10 0010.
 */
public class ItemInfo extends TbItem {
    public String[] getImages(){
        if(getImage() != null){
            return getImage().split(",");
        }
        return null;
    }
}
