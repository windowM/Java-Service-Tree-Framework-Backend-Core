package com.egovframework.ple.treemap.service;

import com.egovframework.ple.treeframework.controller.CommonResponse;
import com.egovframework.ple.treeframework.model.TreeSearchEntity;
import com.egovframework.ple.treemap.dao.GlobalTreeMapRepository;
import com.egovframework.ple.treemap.model.GlobalTreeMapEntity;
import lombok.AllArgsConstructor;
import org.hibernate.CacheMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.unitils.util.ReflectionUtils;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GlobalTreeMapServiceImpl implements GlobalTreeMapService {

    private final GlobalTreeMapRepository globalTreeMapRepository;

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public GlobalTreeMapEntity save(GlobalTreeMapEntity globalTreeMapEntity) {
        return globalTreeMapRepository.save(globalTreeMapEntity);
    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public GlobalTreeMapEntity update(GlobalTreeMapEntity globalTreeMapEntity) {
        return globalTreeMapRepository.save(globalTreeMapEntity);
    }

    @Override
    @Transactional(transactionManager = "transactionJpaManager")
    public Long delete(GlobalTreeMapEntity globalTreeMapEntity) {
        return globalTreeMapRepository.delete(globalTreeMapEntity.getMap_key());
    }

    @Override
    public List<GlobalTreeMapEntity> findAllBy(GlobalTreeMapEntity globalTreeMapEntity) {
        Specification<GlobalTreeMapEntity> searchWith = (root, query, builder) -> builder.and(
                ReflectionUtils.getAllFields(globalTreeMapEntity.getClass()).stream()
                        .filter(
                                field -> {
                                    try {
                                        field.setAccessible(true);
                                        return !ObjectUtils.isEmpty(field.get(globalTreeMapEntity));
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        )
                        .map(
                                field -> {
                                    try {
                                        field.setAccessible(true);
                                        return builder.equal(root.get(field.getName()), field.get(globalTreeMapEntity));
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        ).toArray(Predicate[]::new)
        );

        return globalTreeMapRepository.findAllBy(searchWith).stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public GlobalTreeMapEntity findById(GlobalTreeMapEntity globalTreeMapEntity) {
        return globalTreeMapRepository.findById(globalTreeMapEntity.getMap_key());
    }

}
