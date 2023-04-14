package com.egovframework.ple.treemap.service;

import com.egovframework.ple.treemap.dao.GlobalTreeMapRepository;
import com.egovframework.ple.treemap.model.GlobalTreeMap;
import com.egovframework.ple.treemap.model.GlobalTreeMapSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class GlobalTreeMapServiceImpl implements GlobalTreeMapService {

    private final GlobalTreeMapRepository globalTreeMapRepository;

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public GlobalTreeMap save(GlobalTreeMap globalTreeMap) {
        return globalTreeMapRepository.save(globalTreeMap);
    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public GlobalTreeMap update(GlobalTreeMap globalTreeMap) {
        return globalTreeMapRepository.save(globalTreeMap);
    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public Long delete(GlobalTreeMap globalTreeMap) {
        return globalTreeMapRepository.delete(globalTreeMap.getMap_key());
    }

    @Override
    public List<GlobalTreeMap> findAllBy(Specification<GlobalTreeMap> specification) {
        return globalTreeMapRepository.findAllBy(specification);
    }
}
