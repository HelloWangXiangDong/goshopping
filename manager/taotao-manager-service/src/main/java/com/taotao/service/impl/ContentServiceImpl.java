package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XDStation on 2016/8/3 0003.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Value("#{configProperties['cache_base_url']}")
    private String cache_base_url;
    @Value("#{configProperties['content_url']}")
    private String content_url;
    @Override
    public Page getContentCategoryQuery(Long categoryId, int page, int rows) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        PageHelper.startPage(page,rows);
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contents);
        Long pageSize = (long)pageInfo.getSize();
        Page p = new Page();
        p.setRows(contents);
        p.setTotal(pageSize);
        return p;
    }

    @Override
    public TaotaoResult insertContentCategory(TbContent content) {
        //补全pojo
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

        //同步redis缓存
        HttpClientUtil.doGet(cache_base_url+content_url);

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(String ids) {
        //将ids转换为List
        String[] tempStr = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for(String s : tempStr){
            list.add(Long.parseLong(s));
        }
        TbContentExample example = new TbContentExample();
        example.createCriteria().andIdIn(list);
        contentMapper.deleteByExample(example);

        //同步redis缓存
        HttpClientUtil.doGet(cache_base_url+content_url);

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContent(TbContent content) {
        contentMapper.updateByPrimaryKeySelective(content);

        //同步redis缓存
        HttpClientUtil.doGet(cache_base_url+content_url);

        return TaotaoResult.ok();
    }
}
