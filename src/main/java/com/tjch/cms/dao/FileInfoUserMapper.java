/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: FileInfoUserMapper
 * Author:   Administrator
 * Date:     2019/12/9 14:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.dao;

import com.tjch.cms.pojo.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/12/9
 * @since 1.0.0
 */

@Mapper
public interface  FileInfoUserMapper {


    FileInfo findByFileName(@Param(value = "fileName")String fileName);

    FileInfo findByFileNameAndValid(@Param(value = "fileName") String fileName , @Param(value = "valid")Boolean valid);

    List<FileInfo> findByValid(@Param(value = "valid")Boolean valid);

    List<FileInfo> findByResourceId(@Param(value = "resourceId") String resourceId);

    int deleteAll(@Param(value = "files") List<FileInfo> files);

}