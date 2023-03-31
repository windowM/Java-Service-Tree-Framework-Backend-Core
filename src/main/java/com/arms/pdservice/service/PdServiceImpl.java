/*
 * @author Dongmin.lee
 * @since 2022-06-17
 * @version 22.06.17
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.pdservice.service;

import com.arms.dynamicdbmaker.service.DynamicDBMaker;
import com.arms.filerepository.model.FileRepositoryEntity;
import com.arms.filerepository.service.FileRepository;
import com.arms.filerepository.service.FileRepositoryImpl;
import com.arms.pdservice.model.PdServiceEntity;
import com.arms.pdserviceversion.model.PdServiceVersionEntity;
import com.arms.pdserviceversion.service.PdServiceVersion;
import com.egovframework.ple.treeframework.service.TreeServiceImpl;
import com.egovframework.ple.treeframework.util.Util_TitleChecker;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service("pdService")
public class PdServiceImpl extends TreeServiceImpl implements PdService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Long ROOT_NODE_ID = new Long(2);
    private static final String NODE_TYPE = new String("default");
    private static final String REQ_PREFIX_TABLENAME_BY_PDSERVICE = new String("T_ARMS_REQADD_");
    private static final String REQ_PREFIX_TABLENAME_BY_PDSERVICE_STATUS = new String("T_ARMS_REQSTATUS_");

    @Autowired
    @Qualifier("fileRepository")
    private FileRepository fileRepository;

    @Autowired
    @Qualifier("pdServiceVersion")
    private PdServiceVersion pdServiceVersion;

    @Autowired
    @Qualifier("dynamicDBMaker")
    private DynamicDBMaker dynamicDBMaker;

    @Override
    public List<PdServiceEntity> getNodesWithoutRoot(PdServiceEntity pdServiceEntity) throws Exception {
        pdServiceEntity.setOrder(Order.desc("c_id"));
        Criterion criterion = Restrictions.not(
                // replace "id" below with property name, depending on what you're filtering against
                Restrictions.in("c_id", new Object[] {new Long(1), new Long(2)})
        );
        pdServiceEntity.getCriterions().add(criterion);
        List<PdServiceEntity> list = this.getChildNode(pdServiceEntity);
        for (PdServiceEntity dto: list) {
            dto.setC_pdservice_contents("force empty");
        }
        return list;
    }


    @Override
    public PdServiceEntity addNodeToEndPosition(PdServiceEntity pdServiceEntity) throws Exception {
        //루트 노드를 기준으로 리스트를 검색
        PdServiceEntity paramPdServiceEntity = new PdServiceEntity();
        paramPdServiceEntity.setWhere("c_parentid", ROOT_NODE_ID);
        List<PdServiceEntity> list = this.getChildNode(paramPdServiceEntity);

        //검색된 노드중 maxPosition을 찾는다.
        PdServiceEntity maxPositionPdServiceEntity = list
                .stream()
                .max(Comparator.comparing(PdServiceEntity::getC_position))
                .orElseThrow(NoSuchElementException::new);

        //노드 값 셋팅
        pdServiceEntity.setRef(ROOT_NODE_ID);
        pdServiceEntity.setC_position(maxPositionPdServiceEntity.getC_position() + 1);
        pdServiceEntity.setC_type(NODE_TYPE);

        return this.addNode(pdServiceEntity);
    }

    @Override
    @Transactional
    public PdServiceEntity addPdServiceAndVersion(PdServiceEntity pdServiceEntity) throws Exception {
        pdServiceEntity.setC_title(Util_TitleChecker.StringReplace(pdServiceEntity.getC_title()));

        //Default Version 생성
        PdServiceVersionEntity baseVerNode = new PdServiceVersionEntity();
        baseVerNode.setRef(2L);
        baseVerNode.setC_title("BaseVersion");
        baseVerNode.setC_type("default");
        baseVerNode.setC_pds_version_start_date("start");
        baseVerNode.setC_pds_version_end_date("end");
        baseVerNode.setC_pds_version_contents("contents");
        baseVerNode.setC_pds_version_etc("etc");
        PdServiceVersionEntity baseNode = pdServiceVersion.addNode(baseVerNode);

        PdServiceVersionEntity defaultVerNode = new PdServiceVersionEntity();
        defaultVerNode.setRef(2L);
        defaultVerNode.setC_title("BaseVersion");
        defaultVerNode.setC_type("default");
        defaultVerNode.setC_pds_version_start_date("start");
        defaultVerNode.setC_pds_version_end_date("end");
        defaultVerNode.setC_pds_version_contents("contents");
        defaultVerNode.setC_pds_version_etc("etc");
        PdServiceVersionEntity defaultNode = pdServiceVersion.addNode(defaultVerNode);


        Set<PdServiceVersionEntity> treeset = new HashSet<>();
        treeset.add(baseNode);
        treeset.add(defaultNode);

        pdServiceEntity.setPdServiceVersionEntities(treeset);

        //제품(서비스) 데이터 등록
        PdServiceEntity addedNode = this.addNode(pdServiceEntity);

        //제품(서비스) 생성시 - 요구사항 TABLE 생성
        //제품(서비스) 생성시 - 요구사항 STATUS TABLE 생성
        dynamicDBMaker.createSchema(addedNode.getC_id().toString());

        //C_ETC 컬럼에 요구사항 테이블 이름 기입
        addedNode.setC_pdservice_etc(REQ_PREFIX_TABLENAME_BY_PDSERVICE + addedNode.getC_id().toString());
        this.updateNode(addedNode);

        return addedNode;
    }

    @Override
    @Transactional
    public PdServiceEntity addPdServiceAndVersion2(PdServiceEntity pdServiceEntity) throws Exception {
        pdServiceEntity.setC_title(Util_TitleChecker.StringReplace(pdServiceEntity.getC_title()));

        //Default File 생성
        FileRepositoryEntity fileNode = new FileRepositoryEntity();
        fileNode.setRef(2L);
        fileNode.setC_title("DefaultFile");
        fileNode.setC_type("default");
        FileRepositoryEntity fileRepoNode = fileRepository.addNode(fileNode);

        Set<FileRepositoryEntity> fileset = new HashSet<>();
        fileset.add(fileRepoNode);

        //Default Version 생성
        PdServiceVersionEntity baseVerNode = new PdServiceVersionEntity();
        baseVerNode.setRef(2L);
        baseVerNode.setC_title("BaseVersion");
        baseVerNode.setC_type("default");
        baseVerNode.setC_pds_version_start_date("start");
        baseVerNode.setC_pds_version_end_date("end");
        baseVerNode.setC_pds_version_contents("contents");
        baseVerNode.setC_pds_version_etc("etc");
        PdServiceVersionEntity baseNode = pdServiceVersion.addNode(baseVerNode);

        PdServiceVersionEntity defaultVerNode = new PdServiceVersionEntity();
        defaultVerNode.setRef(2L);
        defaultVerNode.setC_title("BaseVersion");
        defaultVerNode.setC_type("default");
        defaultVerNode.setC_pds_version_start_date("start");
        defaultVerNode.setC_pds_version_end_date("end");
        defaultVerNode.setC_pds_version_contents("contents");
        defaultVerNode.setC_pds_version_etc("etc");
        PdServiceVersionEntity defaultNode = pdServiceVersion.addNode(defaultVerNode);


        Set<PdServiceVersionEntity> treeset = new HashSet<>();
        treeset.add(baseNode);
        treeset.add(defaultNode);

        //pdServiceEntity.setPdServiceVersionEntities(treeset);

        //제품(서비스) 데이터 등록
        PdServiceEntity addedNode = this.addNode(pdServiceEntity);

        //제품(서비스) 생성시 - 요구사항 TABLE 생성
        //제품(서비스) 생성시 - 요구사항 STATUS TABLE 생성
        dynamicDBMaker.createSchema(addedNode.getC_id().toString());

        //C_ETC 컬럼에 요구사항 테이블 이름 기입
        addedNode.setC_pdservice_etc(REQ_PREFIX_TABLENAME_BY_PDSERVICE + addedNode.getC_id().toString());

        addedNode.setPdServiceVersionEntities(treeset);
        addedNode.setFileRepositoryEntities(fileset);

        this.updateNode(addedNode);

        return addedNode;
    }
}