package com.egovframework.ple.treemap.dao;

import com.egovframework.ple.treemap.model.GlobalTreeMap;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GlobalTreeMapRepository {

    private final GlobalTreeMapJpaRepository globalTreeMapJpaRepository;

    public GlobalTreeMap save(GlobalTreeMap globalTreeMap) {
        GlobalTreeMap savedGlobalTreeMap = globalTreeMapJpaRepository.save(globalTreeMap);
        return savedGlobalTreeMap;
    }

    public Long delete(Long map_key) {
        globalTreeMapJpaRepository.deleteById(map_key);
        return map_key;
    }

    public GlobalTreeMap findById(Long map_key) {
        return globalTreeMapJpaRepository.findById(map_key)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<GlobalTreeMap> findAllBy(Specification<GlobalTreeMap> specification){
        return globalTreeMapJpaRepository.findAll(specification);
    }

    public Page<GlobalTreeMap> findAllBy(Specification<GlobalTreeMap> specification, Pageable pageable){
        return globalTreeMapJpaRepository.findAll(specification,pageable);
    }
}
