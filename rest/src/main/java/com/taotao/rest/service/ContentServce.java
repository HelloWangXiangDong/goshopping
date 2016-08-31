package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by XDStation on 2016/8/4 0004.
 */
public interface ContentServce {
    //根据category查询分类列表
    List<TbContent> getContent(Long categoryId);
}
