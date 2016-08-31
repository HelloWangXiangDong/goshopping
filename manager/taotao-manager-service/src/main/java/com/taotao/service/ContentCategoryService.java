package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * Created by XDStation on 2016/8/3 0003.
 * 商品内容类目管理
 */
public interface ContentCategoryService {
    //查询
    List<EUTreeNode> getContentCategory(Long parentId);
    //添加
    TaotaoResult insertContentCategory(Long parentId,String name);
    //删除
    TaotaoResult deleteContentCategory(Long parentId,Long id);
    //修改
    TaotaoResult updateContentCategory(Long id,String name);
}
