package com.taotao.rest.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisDao;
import com.taotao.rest.service.ContentServce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XDStation on 2016/8/4 0004.
 */
@Service
public class ContentServiceImpl implements ContentServce {
    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisDao jedisDao;

    @Value("#{configProperties['Index_Content_Redis_Key']}")
    private String Index_Content_Redis_Key;
    @Override
    public List<TbContent> getContent(Long categoryId) {
        //从缓存中取数据
        try {
            String hget = jedisDao.hget(Index_Content_Redis_Key, categoryId + "");
            if(!StringUtils.isEmpty(hget)){
                List<TbContent> contents = JsonUtils.jsonToList(hget, TbContent.class);
                return contents;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.getStackTrace(e);
        }

        //根据Id查询内容列表
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> contents = contentMapper.selectByExample(example);

        //向缓存中写入数据
        try {
            String s = JsonUtils.objectToJson(contents);
            jedisDao.hset(Index_Content_Redis_Key,categoryId+"",s);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.getStackTrace(e);
        }
        return contents;
    }
}
