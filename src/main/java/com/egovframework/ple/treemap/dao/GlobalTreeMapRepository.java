package com.egovframework.ple.treemap.dao;

import com.egovframework.ple.treemap.model.GlobalTreeMapEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
@AllArgsConstructor
public class GlobalTreeMapRepository {

    private final GlobalTreeMapJpaRepository globalTreeMapJpaRepository;

    public GlobalTreeMapEntity save(GlobalTreeMapEntity globalTreeMapEntity) {
        GlobalTreeMapEntity savedGlobalTreeMapEntity = globalTreeMapJpaRepository.save(globalTreeMapEntity);
        return savedGlobalTreeMapEntity;
    }

    public Long delete(Long map_key) {
        globalTreeMapJpaRepository.deleteById(map_key);
        return map_key;
    }

    public GlobalTreeMapEntity findById(Long map_key) {
        return globalTreeMapJpaRepository.findById(map_key)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<GlobalTreeMapEntity> findAllBy(Specification<GlobalTreeMapEntity> specification){
        return globalTreeMapJpaRepository.findAll(specification);
    }

    public Page<GlobalTreeMapEntity> findAllBy(Specification<GlobalTreeMapEntity> specification, Pageable pageable){
        return globalTreeMapJpaRepository.findAll(specification,pageable);
    }
}
