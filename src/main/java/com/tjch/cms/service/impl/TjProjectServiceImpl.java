/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TjProjectServiceImpl
 * Author:   Administrator
 * Date:     2019/11/28 15:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.service.impl;

import com.tjch.cms.base.BaseApiService;
import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.dao.TjProjectMapper;
import com.tjch.cms.dao.TjProjectUserMapper;
import com.tjch.cms.model.ProGroupModel;
import com.tjch.cms.model.ResultListModel;
import com.tjch.cms.model.TjProjectModel;
import com.tjch.cms.pojo.TjProject;
import com.tjch.cms.service.TjProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/11/28
 * @since 1.0.0
 */
@Service(value="tjProjectService")
@Transactional
public class TjProjectServiceImpl extends BaseApiService implements TjProjectService{
    @Autowired
    private TjProjectUserMapper tjProjectUserMapper;

    @Autowired
    private TjProjectMapper tjProjectMapper;

    @Override
    public ResponseBase findAllPro(TjProjectModel model) {
        ResponseBase res = new ResponseBase();
        List<TjProject> list = tjProjectUserMapper.findAll(model);
        Long total = tjProjectUserMapper.findAllCount(model);
        ResultListModel reList = new ResultListModel();
        reList.setPageIndex(model.getPageIndex());
        reList.setPageSize(model.getPageSize());
        reList.setTotal(total);
        reList.setList(list);
        res.setData(reList);
        return res;
    }

    @Override
    public ResponseBase insertPro(TjProject pro) {
        ResponseBase res = new ResponseBase();
        Integer tag = tjProjectMapper.insertSelective(pro);
        res.setCode(200);
        res.setMsg("添加成功");
        res.setData(tag);
        return res;
    }

    @Override
    public ResponseBase updatePro(TjProject pro) {
        ResponseBase res = new ResponseBase();
        Integer tag = tjProjectMapper.updateByPrimaryKeySelective(pro);
        res.setMsg("编辑成功");
        res.setCode(200);
        res.setData(tag);
        return res;
    }

    @Override
    public ResponseBase deletePro(Integer... ids) {
        Integer tag = tjProjectUserMapper.deleteByIds(ids);
        ResponseBase res = new ResponseBase();
        res.setMsg("删除成功");
        res.setCode(200);
        res.setData(tag);
        return res;
    }

    @Override
    public ResponseBase findById(Integer id) {
        TjProject data = tjProjectUserMapper.findById(id);
        ResponseBase res = new ResponseBase();
        res.setMsg("查询成功");
        res.setCode(200);
        res.setData(data);
        return res;
    }

    @Override
    public ResponseBase findGroupProCount(TjProjectModel model) {
        ResponseBase res = new ResponseBase();
        List<ProGroupModel> proGroupModel = tjProjectUserMapper.findGroupProCount(model);
        res.setMsg("查询成功");
        res.setCode(200);
        res.setData(proGroupModel);
        return res;
    }
}