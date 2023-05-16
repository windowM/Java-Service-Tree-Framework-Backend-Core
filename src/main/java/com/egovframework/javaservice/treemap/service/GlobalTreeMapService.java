package com.egovframework.javaservice.treemap.service;

import com.egovframework.javaservice.treemap.model.GlobalTreeMapEntity;

import java.util.List;

public interface GlobalTreeMapService {

    GlobalTreeMapEntity save(GlobalTreeMapEntity globalTreeMapEntity);

    GlobalTreeMapEntity update(GlobalTreeMapEntity globalTreeMapEntity);

    Long delete(GlobalTreeMapEntity globalTreeMapEntity);

    List<GlobalTreeMapEntity> findAllBy(GlobalTreeMapEntity globalTreeMapEntity);

    GlobalTreeMapEntity findById(GlobalTreeMapEntity globalTreeMapEntity);

}
