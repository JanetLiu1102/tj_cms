/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ResultListModel
 * Author:   Administrator
 * Date:     2019/11/29 10:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.model;

import com.tjch.cms.pojo.TjProject;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/11/29
 * @since 1.0.0
 */
public class ResultListModel extends BaseResultModel{

    private List<TjProject> list;

    public List<TjProject> getList() {
        return list;
    }

    public void setList(List<TjProject> list) {
        this.list = list;
    }
}