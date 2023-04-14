package com.egovframework.ple.treemap.dao;

import com.egovframework.ple.treemap.model.GlobalTreeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GlobalTreeMapJpaRepository extends JpaRepository<GlobalTreeMap,Long>, JpaSpecificationExecutor<GlobalTreeMap> {
}
