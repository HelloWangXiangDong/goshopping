package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by XDStation on 2016/8/8 0008.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllItems() {
        try {

            //查询商品列表
            List<Item> list = itemMapper.getSearchResult();
            //把商品信息写入索引库
            for (Item item : list) {
                //创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id", item.getId());
                document.setField("item_title", item.getTitle());
                document.setField("item_sell_point", item.getSell_point());
                document.setField("item_price", item.getPrice());
                document.setField("item_image", item.getImage());
                document.setField("item_category_name", item.getCategory_name());
                document.setField("item_desc", item.getItem_des());
                //写入索引库
                solrServer.add(document);
            }
            //提交修改
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addItem(Item item) {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", item.getId());
        document.setField("item_title", item.getTitle());
        document.setField("item_sell_point", item.getSell_point());
        document.setField("item_price", item.getPrice());
        document.setField("item_image", item.getImage());
        document.setField("item_category_name", item.getCategory_name());
        document.setField("item_desc", item.getItem_des());
        //写入索引库
        try {
            solrServer.add(document);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"向solr插入数据出错！");
        } catch (IOException e) {
            e.printStackTrace();
            return TaotaoResult.build(500,"向solr插入数据出错！");
        }
        return TaotaoResult.ok();
    }

}
