package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XDStation on 2016/8/4 0004.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("#{configProperties['REST_BASE_URL']}")
    private String REST_BASE_URL;
    @Value("#{configProperties['REST_INDEX_AD_URL']}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {

        try {
            //发get请求，返回json字符串
            String s = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
            //转换字符串为List
            TaotaoResult result = TaotaoResult.formatToList(s, TbContent.class);
            //强转List
            List<TbContent> list = (List<TbContent>)result.getData();
            //声明一个用于接收参数的List，将来会把这个List封装成Json数据
            List<Map> mapList = new ArrayList<Map>();
            //取出get来的参数，并添加到结果中
            for(TbContent t : list){
                Map map = new HashMap();
                map.put("srcB",t.getPic2());
                map.put("height",240);
                map.put("alt",t.getSubTitle());
                map.put("width",670);
                map.put("src",t.getPic());
                map.put("widthB",550);
                map.put("href",t.getUrl());
                map.put("heightB",240);
                mapList.add(map);
            }
            String s1 = JsonUtils.objectToJson(mapList);
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
