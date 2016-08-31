package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.Page;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
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
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getContentCategory(Long parentId) {
        //根据parentId查询数据
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //遍历list生成EUTreeNode形式的List
        List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
        for (TbContentCategory cc : list){
            EUTreeNode node = new EUTreeNode();
            node.setId(cc.getId());
            node.setParentId(cc.getParentId());
            node.setText(cc.getName());
            node.setState(cc.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(Long parentId, String name) {
        //创建一个pojo
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        contentCategory.setUpdated(new Date());
        contentCategory.setCreated(new Date());
        contentCategory.setParentId(parentId);
        //添加记录
        contentCategoryMapper.insert(contentCategory);
        //看一看父节点的isParent是否为true，不是的话改成true
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!category.getIsParent()){
            category.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(category);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContentCategory(Long parentId, Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);
        //先判断一下有没有子类目
        List<TbContentCategory> tempList = contentCategoryMapper.selectByExample(example);
        if(tempList != null && tempList.size() > 0){
            //删除子类目
            contentCategoryMapper.deleteByExample(example);
        }
        //删除自己
        contentCategoryMapper.deleteByPrimaryKey(id);
        //再判断一下它的父节点是否还有子数据,没有数据的话把isParent弄成false
        TbContentCategoryExample parent = new TbContentCategoryExample();
        parent.createCriteria().andParentIdEqualTo(parentId);
        tempList.clear();
        tempList = contentCategoryMapper.selectByExample(parent);
        if(tempList == null || tempList.size() <= 0){
            TbContentCategory parent1 = new TbContentCategory();
            parent1 = contentCategoryMapper.selectByPrimaryKey(parentId);
            parent1.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parent1);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andIdEqualTo(id);
        TbContentCategory category = new TbContentCategory();
        category.setName(name);
        contentCategoryMapper.updateByExampleSelective(category,example);
        return TaotaoResult.ok();
    }
}
