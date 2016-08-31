package com.taotao.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by XDStation on 2016/7/29 0029.
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper paramItemMapper;
    @Override
    public String getItemParamItem(Long cid) {
        //根据ID查询
        TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
        itemParamItemExample.createCriteria().andItemIdEqualTo(cid);
        //执行查询
        List<TbItemParamItem> list = paramItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
        if(list == null || list.size() == 0){
            return "";
        }
        //取规格参数
        TbItemParamItem itemParamItem = list.get(0);
        String data = itemParamItem.getParamData();
        //生成HTML,把规格参数转化为java对象
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
    }
}