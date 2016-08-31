package com.taotao.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XDStation on 2016/7/25 0025.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 根据ID查询
     * @param parentId
     * @return
     */
    @Override
    public List<TbItemCat> getItemCatList(Long parentId) {
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(itemCatExample);
        return list;
    }

    @Override
    public TbItemCat getItemCatById(Long cid) {
        TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(cid);
        return tbItemCat;
    }
}
