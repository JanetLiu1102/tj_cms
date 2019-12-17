package com.tjch.cms.dao;

import com.tjch.cms.model.ProGroupModel;
import com.tjch.cms.model.TjProjectModel;
import com.tjch.cms.pojo.TjProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TjProjectUserMapper {
    List<TjProject> findAll(TjProjectModel model);

    TjProject findById(Integer id);

    Long findAllCount(TjProjectModel model);

    Integer deleteByIds(@Param(value = "ids")Integer...ids);

    List<ProGroupModel> findGroupProCount(TjProjectModel model);
}
