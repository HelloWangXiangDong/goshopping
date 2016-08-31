package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by XDStation on 2016/8/9 0009.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("#{configProperties['REST_BASE_URL']}")
    private String REST_BASE_URL;

    @Value("#{configProperties[item_baseInfo_url]}")
    private String item_baseInfo_url;

    @Value("#{configProperties['item_ItemParam_url']}")
    private String item_ItemParam_url;

    @Value("#{configProperties['item_ItemDesc_url']}")
    private String item_ItemDesc_url;

    @Override
    public ItemInfo getItemById(Long id) {
        try {
            String s = HttpClientUtil.doGet(REST_BASE_URL+item_baseInfo_url+id);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(s, ItemInfo.class);
            ItemInfo item = (ItemInfo)taotaoResult.getData();
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TbItemDesc getItemDesc(Long itemId) {
        try {
            String s = HttpClientUtil.doGet(REST_BASE_URL + item_ItemDesc_url + itemId);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(s, TbItemDesc.class);

            TbItemDesc itemDesc = (TbItemDesc)taotaoResult.getData();
            return itemDesc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemParam(Long itemId) {
        try {
            String s = HttpClientUtil.doGet(REST_BASE_URL + item_ItemParam_url + itemId);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(s, TbItemParamItem.class);
            TbItemParamItem itemParamItem = (TbItemParamItem)taotaoResult.getData();
            //生成HTML,把规格参数转化为java对象
            String data = itemParamItem.getParamData();
            List<Map> mapData = JsonUtils.jsonToList(data, Map.class);
            StringBuffer sb = new StringBuffer();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\"\n");
            sb.append("		class=\"Ptable\">\n");
            sb.append("		<tbody>\n");
            for(Map m1 : mapData) {
                sb.append("			<tr>\n");
                sb.append("				<th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                sb.append("			</tr>\n");
                List<Map> myData = (List<Map>) m1.get("params");
                for(Map m2 : myData){
                    sb.append("<tr>\n");
                    sb.append("<td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                    sb.append("<td>"+m2.get("v")+"</td>\n");
                }
                sb.append("</tr>\n");
            }
            sb.append("</tbody>\n" );
            sb.append("</table>");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
