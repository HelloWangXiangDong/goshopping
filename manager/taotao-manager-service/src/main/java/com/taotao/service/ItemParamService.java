package com.taotao.service;

import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

import java.util.List;

/**
 * Created by XDStation on 2016/7/26 0026.
 */
public interface ItemParamService {
    //商品规格类目service
    TaotaoResult getItemParamById(Long id);
    //插入商品类目
    TaotaoResult saveItemParam(TbItemParam itemParam);
    //查询商品类目列表信息
    Page geItemParam(int page,int rows);
    //删除商品参数
    TaotaoResult deleteItemParam(String ids);
}
