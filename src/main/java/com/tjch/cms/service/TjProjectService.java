package com.tjch.cms.service;

import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.model.TjProjectModel;
import com.tjch.cms.pojo.TjProject;

public interface TjProjectService {
    ResponseBase findAllPro(TjProjectModel model);

    ResponseBase insertPro(TjProject pro);

    ResponseBase updatePro(TjProject pro);

    ResponseBase deletePro(Integer...ids);

    ResponseBase findById(Integer id);

    ResponseBase findGroupProCount(TjProjectModel model);
}
