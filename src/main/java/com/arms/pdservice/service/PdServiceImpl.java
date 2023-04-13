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
import com.arms.pdservice.model.PdServiceEntity;
import com.arms.pdserviceversion.model.PdServiceVersionEntity;
import com.arms.pdserviceversion.service.PdServiceVersion;
import com.egovframework.ple.treeframework.service.TreeServiceImpl;
import com.egovframework.ple.treeframework.util.*;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

        Set<PdServiceVersionEntity> treeset = new HashSet<>();
        treeset.add(baseNode);

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
    public PdServiceEntity addPdServiceVersion(PdServiceEntity pdServiceEntity) throws Exception {

        PdServiceEntity pdServiceNode = this.getNode(pdServiceEntity);
        Set<PdServiceVersionEntity> versionNodes = pdServiceNode.getPdServiceVersionEntities();
        Set<FileRepositoryEntity> fileNodes = pdServiceNode.getFiles();

        pdServiceNode.setFiles(fileNodes);
        pdServiceNode.setPdServiceVersionEntities(new HashSet<>());
        this.updateNode(pdServiceNode);

        Set<PdServiceVersionEntity> targetVersionNode = pdServiceEntity.getPdServiceVersionEntities();
        Set<PdServiceVersionEntity> addedNode = new HashSet<>();
        for( PdServiceVersionEntity versionEntity : targetVersionNode ){

            String compareTitle = versionEntity.getC_title();
            Optional<PdServiceVersionEntity> searching = versionNodes.stream().filter(node -> StringUtils.equalsIgnoreCase(compareTitle, node.getC_title())).findFirst();

            if( searching.isEmpty()){
                PdServiceVersionEntity addedVersion = pdServiceVersion.addNode(versionEntity);
                addedNode.add(addedVersion);
            }

        }

        Set<PdServiceVersionEntity> mergedSet = new HashSet<>();
        mergedSet.addAll(versionNodes);
        mergedSet.addAll(addedNode);


        pdServiceNode.setFiles(fileNodes);
        pdServiceNode.setPdServiceVersionEntities(mergedSet);

        this.updateNode(pdServiceNode);

        return pdServiceNode;
    }

    @Override
    @Transactional
    public PdServiceEntity uploadFileTo(Long param_c_id, MultipartHttpServletRequest multiRequest) throws Exception {

        Set<FileRepositoryEntity> fileEntitySet = upload(multiRequest, "test", fileRepository, logger);

        PdServiceEntity paramPdServiceNode = new PdServiceEntity();
        paramPdServiceNode.setC_id(param_c_id);

        PdServiceEntity updateTarget = this.getNode(paramPdServiceNode);

        String c_title = "pdService";

        updateTarget.setFiles(fileEntitySet);
        this.updateNode(updateTarget);

        return updateTarget;
    }

    public Set<FileRepositoryEntity> upload(MultipartHttpServletRequest multiRequest,
                                                   String c_title,
                                                   FileRepository fileRepository,
                                                   Logger logger) throws Exception {

        logger.info("FileHandler :: upload :: c_title -> " + c_title);

        // Spring multipartResolver 미사용 시 (commons-fileupload 활용)
        //List<EgovFormBasedFileVo> list = EgovFormBasedFileUtil.uploadFiles(request, uploadDir, maxFileSize);

        // Spring multipartResolver 사용시
        PropertiesReader propertiesReader = new PropertiesReader("com/egovframework/property/globals.properties");
        String uploadDir = propertiesReader.getProperty("Globals.fileStorePath");
        long maxFileSize = new Long(313);
        List<EgovFormBasedFileVo> list = EgovFileUploadUtil.uploadFiles(multiRequest, uploadDir, maxFileSize);

        Set<FileRepositoryEntity> fileRepositoryEntities = new HashSet<>();

        for (EgovFormBasedFileVo egovFormBasedFileVo : list) {

            FileRepositoryEntity fileRepositoryEntity = new FileRepositoryEntity();
            fileRepositoryEntity.setFileName(egovFormBasedFileVo.getFileName());
            fileRepositoryEntity.setContentType(egovFormBasedFileVo.getContentType());
            fileRepositoryEntity.setServerSubPath(egovFormBasedFileVo.getServerSubPath());
            fileRepositoryEntity.setPhysicalName(egovFormBasedFileVo.getPhysicalName());
            fileRepositoryEntity.setSize(egovFormBasedFileVo.getSize());
            fileRepositoryEntity.setName(egovFormBasedFileVo.getName());

            fileRepositoryEntity.setUrl(egovFormBasedFileVo.getUrl());
            //TODO: 썸네일 개발 필요
            fileRepositoryEntity.setThumbnailUrl(egovFormBasedFileVo.getThumbnailUrl());

            fileRepositoryEntity.setDelete_url(egovFormBasedFileVo.getDelete_url());
            fileRepositoryEntity.setDelete_type(egovFormBasedFileVo.getDelete_type());

            fileRepositoryEntity.setRef(new Long(2));
            fileRepositoryEntity.setC_title(c_title);
            fileRepositoryEntity.setC_type("default");

            FileRepositoryEntity returnFileRepositoryEntity = fileRepository.addNode(fileRepositoryEntity);
            //delete 파라미터인 id 값을 업데이트 치기 위해서.
            fileRepositoryEntity.setUrl("/auth-user/api/arms/fileRepository" + "/downloadFileByNode/" + returnFileRepositoryEntity.getId());
            fileRepositoryEntity.setThumbnailUrl("/auth-user/api/arms/fileRepository" + "/thumbnailUrlFileToNode/" + returnFileRepositoryEntity.getId());
            fileRepositoryEntity.setDelete_url("/auth-user/api/arms/fileRepository" + "/deleteFileByNode/" + returnFileRepositoryEntity.getId());

            fileRepository.updateNode(fileRepositoryEntity);

            fileRepositoryEntities.add(fileRepositoryEntity);

            egovFormBasedFileVo.setUrl("/auth-user/api/arms/fileRepository" + "/downloadFileByNode/" + returnFileRepositoryEntity.getId());
            egovFormBasedFileVo.setThumbnailUrl("/auth-user/api/arms/fileRepository" + "/thumbnailUrlFileToNode/" + returnFileRepositoryEntity.getId());
            egovFormBasedFileVo.setDelete_url("/auth-user/api/arms/fileRepository" + "/deleteFileByNode/" + returnFileRepositoryEntity.getId());

        }

        //HashMap<String, List<EgovFormBasedFileVo>> map = new HashMap();
        //map.put("files", list);
        //return map;
        return fileRepositoryEntities;
    }

    @Override
    @Transactional
    public PdServiceVersionEntity removeVersionNode(long param_c_id, PdServiceVersionEntity pdServiceVersionEntity) throws Exception {
        PdServiceEntity pdServiceEntity = new PdServiceEntity();
        pdServiceEntity.setC_id(param_c_id);
        PdServiceEntity pdNode = this.getNode(pdServiceEntity);

        Set<PdServiceVersionEntity> versionSet = pdNode.getPdServiceVersionEntities();
        for ( PdServiceVersionEntity verNode : versionSet){

            if ( verNode.getC_id() == pdServiceVersionEntity.getC_id() ) {
                versionSet.remove(verNode);
            }
        }

        pdNode.setPdServiceVersionEntities(versionSet);
        this.updateNode(pdNode);

        pdServiceVersion.removeNode(pdServiceVersionEntity);
        return pdServiceVersionEntity;
    }
}