package com.taotao.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by XDStation on 2016/7/16 0016.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private ItemCatService itemCatService;

    @Value("#{configProperties['solr_base_url']}")
    private String solr_base_url;
    @Value("#{configProperties['solr_add_url']}")
    private String solr_addItem_url;
    @Value("#{configProperties['solr_delete']}")
    private String solr_delete;
    @Value("#{configProperties['cache_base_url']}")
    private String cache_base_url;
    @Value("#{configProperties['rest_deleteItem']}")
    private String rest_deleteItem;
    //根据ID查询商品
    @Override
    public TbItem getItemById(long itemId) {
        TbItemExample itemExample = new TbItemExample();
        TbItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = itemMapper.selectByExample(itemExample);
        if(list.size() > 0 && list != null){
            return list.get(0);
        }
        return null;
    }
    //查询商品列表
    @Override
    public Page getItemList(int page, int rows) {
        //创建一个Item实例
        TbItemExample itemExample = new TbItemExample();
        //创建分页
        PageHelper.startPage(page,rows);
        //执行查询
        List<TbItem> tbItems = itemMapper.selectByExample(itemExample);
        //返回分页信息
        Page p = new Page();
        p.setTotal(new PageInfo<TbItem>(tbItems).getTotal());
        p.setRows(tbItems);
        return p;
    }

    /**
     * 添加商品
     * @param item
     * @param desc
     * @param itemParams
     * @return
     * @throws Exception
     */
    @Override
    public TaotaoResult saveItem(TbItem item,String desc, String itemParams) throws Exception {
        //补全item中的属性
        //生成商品ID
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态，1-正常 2-下架 3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入到数据库
        itemMapper.insert(item);
        //插入详情
        TaotaoResult taotaoResult = saveItemDesc(itemId,desc);
        //插入规格参数
        taotaoResult = saveItemParam(itemId,itemParams);
        if(taotaoResult.getStatus() != 200){
            throw new Exception();
        }
        try {
            addSolrItem(item,desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    /**
     * 修改商品
     * @param item
     * @param desc
     * @param itemParams
     * @return
     */
    @Override
    public TaotaoResult updateItem(TbItem item, String desc, String itemParams) {
        //补全商品信息
        item.setUpdated(new Date());
        //商品信息
        itemMapper.updateByPrimaryKeySelective(item);
        //商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
        //同步solr
        addSolrItem(item,desc);
        //同步redis
        HttpClientUtil.doGet(cache_base_url+rest_deleteItem+item.getId());
        return TaotaoResult.ok();
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteItem(String ids) {
        String[] idss = ids.split(",");
        TbItemExample example = new TbItemExample();
        List<Long> list = new ArrayList<Long>();
        for(String s : idss){
            list.add(Long.parseLong(s));
            HttpClientUtil.doGet(cache_base_url+rest_deleteItem+s);
        }
        example.createCriteria().andIdIn(list);
        //删除商品
        itemMapper.deleteByExample(example);
        TbItemDescExample descExample = new TbItemDescExample();
        TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
        //删除详情
        descExample.createCriteria().andItemIdIn(list);
        itemDescMapper.deleteByExample(descExample);
        //删除描述
        itemParamItemExample.createCriteria().andItemIdIn(list);
        itemParamItemMapper.deleteByExample(itemParamItemExample);
        // 同步 solr
        HttpClientUtil.doGet(solr_base_url+solr_delete+ids);
        return TaotaoResult.ok();
    }

    /**
     * 加载商品详情
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getDesc(Long itemId) {
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return TaotaoResult.ok(itemDesc);
    }

    /**
     * 存储商品详情
     * @param id
     * @param desc
     * @return
     * @throws Exception
     */
    private TaotaoResult saveItemDesc(Long id,String desc){
        if(!StringUtils.isEmpty(desc)) {
            TbItemDesc itemDesc = new TbItemDesc();
            itemDesc.setItemId(id);
            itemDesc.setItemDesc(desc);
            itemDesc.setUpdated(new Date());
            itemDesc.setCreated(new Date());
            itemDescMapper.insert(itemDesc);
        }
        return TaotaoResult.ok();
    }

    /**
     * 存储商品规格
     * @param itemId
     * @param paramData
     * @return
     */
    private TaotaoResult saveItemParam(Long itemId,String paramData){
        if(!StringUtils.isEmpty(paramData)) {
            TbItemParamItem itemParamItem = new TbItemParamItem();
            itemParamItem.setItemId(itemId);
            itemParamItem.setParamData(paramData);
            itemParamItem.setCreated(new Date());
            itemParamItem.setUpdated(new Date());
            itemParamItemMapper.insert(itemParamItem);
        }
        return TaotaoResult.ok();
    }

    /**
     * 更新solr搜索信息
     * @param item
     */
    private void addSolrItem(TbItem item,String desc){
        //向solr中添加索引库
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",item.getId()+"");
        map.put("title",item.getTitle());
        map.put("sell_point",item.getSellPoint());
        map.put("price",item.getPrice()+"");
        map.put("image",item.getImage());
        map.put("category_name",itemCatService.getItemCatById(item.getCid()).getName());
        map.put("item_des",desc);
        HttpClientUtil.doGet(solr_base_url+solr_addItem_url,map);
    }
}
