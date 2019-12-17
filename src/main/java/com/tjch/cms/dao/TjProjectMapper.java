package com.tjch.cms.dao;


import com.tjch.cms.pojo.TjProject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TjProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TjProject record);

    int insertSelective(TjProject record);

    TjProject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TjProject record);

    int updateByPrimaryKey(TjProject record);
}