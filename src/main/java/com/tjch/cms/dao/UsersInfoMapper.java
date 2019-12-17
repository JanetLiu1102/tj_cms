package com.tjch.cms.dao;


import com.tjch.cms.pojo.UsersInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UsersInfo record);

    int insertSelective(UsersInfo record);

    UsersInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UsersInfo record);

    int updateByPrimaryKey(UsersInfo record);
}