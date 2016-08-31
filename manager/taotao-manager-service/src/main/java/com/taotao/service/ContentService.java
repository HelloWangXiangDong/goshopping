package com.taotao.service;

import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Created by XDStation on 2016/8/3 0003.
 * 商品具体内容编辑
 */
public interface ContentService {
    //显示列表
    Page getContentCategoryQuery(Long categoryId, int page, int rows);
    //添加内容
    TaotaoResult insertContentCategory(TbContent content);
    //删除内容
    TaotaoResult deleteContent(String ids);
    //修改内容
    TaotaoResult updateContent(TbContent content);
}
