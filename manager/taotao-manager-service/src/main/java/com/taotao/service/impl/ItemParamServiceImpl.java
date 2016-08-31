package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.mapper.myMapper.ItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by XDStation on 2016/7/26 0026.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;
    @Autowired
    private ItemParamMapper ParamMapper_1;
    @Value("#{configProperties['cache_base_url']}")
    private String cache_base_url;

    @Value("#{configProperties['itemCat_url']}")
    private String itemCat_url;

    @Override
    public TaotaoResult getItemParamById(Long id) {
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(id);
        //查询的时候需要添加大文本信息用selectByExampleWithBLOBS
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        if(list != null && list.size()>0){
            return TaotaoResult.ok(list.get(0));
        }
        return TaotaoResult.ok();
    }

    /**
     * 存储商品
     * @return
     */
    @Override
    public TaotaoResult saveItemParam(TbItemParam itemParam) {
        //补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);

        //清空首页商品分类列表
        try {
            HttpClientUtil.doGet(cache_base_url+itemCat_url);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return TaotaoResult.ok();
    }

    /**
     * 查询所有规格参数信息
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Page geItemParam(int page,int rows) {
        Page p = new Page();
        PageHelper.startPage(page,rows);
        List<TbItemParam> tbItems = new ArrayList<TbItemParam>();
        tbItems = ParamMapper_1.getItemMapper();
        PageInfo pageInfo = new PageInfo(tbItems);
        Long total = pageInfo.getTotal();
        p.setRows(tbItems);
        p.setTotal(total);
        return p;
    }

    /**
     * 批量删除商品类目
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteItemParam(String ids) {
        //将ids转换为list
        List<Long> list = new ArrayList<Long>();
        String[] strs = ids.split(",");
        for(String str : strs){
            list.add(Long.valueOf(str));
        }
        //创建一个example
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andIdIn(list);
        //调用mapper删除
        itemParamMapper.deleteByExample(example);

        //清空首页商品分类列表
        try {
            HttpClientUtil.doGet(cache_base_url+itemCat_url);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return TaotaoResult.ok();
    }
}
