package com.egovframework.ple.treemap.service;

import com.egovframework.ple.treemap.model.GlobalTreeMapEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GlobalTreeMapService {

    GlobalTreeMapEntity save(GlobalTreeMapEntity globalTreeMapEntity);

    GlobalTreeMapEntity update(GlobalTreeMapEntity globalTreeMapEntity);

    Long delete(GlobalTreeMapEntity globalTreeMapEntity);

    List<GlobalTreeMapEntity> findAllBy(GlobalTreeMapEntity globalTreeMapEntity);

    GlobalTreeMapEntity findById(GlobalTreeMapEntity globalTreeMapEntity);

}
