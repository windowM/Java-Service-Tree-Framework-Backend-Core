package com.egovframework.ple.treemap.dao;

import com.egovframework.ple.treemap.model.GlobalTreeMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GlobalTreeMapJpaRepository extends JpaRepository<GlobalTreeMapEntity,Long>, JpaSpecificationExecutor<GlobalTreeMapEntity> {
}
