package com.taotao.service;

import com.taotao.pojo.TbItemCat;

import java.util.List;

/**
 * Created by XDStation on 2016/7/25 0025.
 */
public interface ItemCatService {
    List<TbItemCat> getItemCatList(Long parentId);
    TbItemCat getItemCatById(Long cid);
}
