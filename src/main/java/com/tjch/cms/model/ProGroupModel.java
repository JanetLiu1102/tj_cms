/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ProGroupModel
 * Author:   Administrator
 * Date:     2019/12/17 9:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.model;

import java.io.Serializable;

/**
 *
 * @author Administrator
 * @create 2019/12/17
 */
public class ProGroupModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String proName;
    private Integer proCount;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
    }
}