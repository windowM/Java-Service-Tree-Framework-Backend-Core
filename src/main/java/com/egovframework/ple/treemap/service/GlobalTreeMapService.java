package com.egovframework.ple.treemap.service;

import com.egovframework.ple.treemap.model.GlobalTreeMap;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GlobalTreeMapService {

    GlobalTreeMap save(GlobalTreeMap globalTreeMap);

    GlobalTreeMap update(GlobalTreeMap globalTreeMap);

    Long delete(GlobalTreeMap globalTreeMap);

    List<GlobalTreeMap> findAllBy(Specification<GlobalTreeMap> specification);

}
