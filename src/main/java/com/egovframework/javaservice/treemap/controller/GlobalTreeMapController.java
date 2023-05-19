package com.egovframework.javaservice.treemap.controller;

import com.egovframework.javaservice.treeframework.controller.CommonResponse;
import com.egovframework.javaservice.treeframework.util.ParameterParser;
import com.egovframework.javaservice.treeframework.util.StringUtils;
import com.egovframework.javaservice.treemap.model.GlobalTreeMapEntity;
import com.egovframework.javaservice.treemap.service.GlobalTreeMapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/arms/globaltreemap")
@RestController
@AllArgsConstructor
@Slf4j
public class GlobalTreeMapController {

    private final GlobalTreeMapService globalTreeMapService;

    @ResponseBody
    @RequestMapping(
            value = {"/getAllGlobalTreeMap.do"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getAllGlobalTreeMap(GlobalTreeMapEntity globalTreeMapEntity, ModelMap model, HttpServletRequest request) throws Exception {

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.findAllBy(globalTreeMapEntity)));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/addGlobalTreeMap.do"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?> addGlobalTreeMap(GlobalTreeMapEntity globalTreeMapEntity, ModelMap model, HttpServletRequest request) throws Exception {

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.save(globalTreeMapEntity)));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/alterGlobalTreeMap.do"},
            method = {RequestMethod.PUT}
    )
    public ResponseEntity<?> alterGlobalTreeMap(GlobalTreeMapEntity reqGlobalTreeMapEntity, ModelMap model, HttpServletRequest request) throws Exception {

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.update(reqGlobalTreeMapEntity)));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/removeGlobalTreeMap.do"},
            method = {RequestMethod.DELETE}
    )
    public ResponseEntity<?> removeGlobalTreeMap(GlobalTreeMapEntity globalTreeMapEntity, ModelMap model, HttpServletRequest request) throws Exception {

        List<GlobalTreeMapEntity> globalTreeMapEntityList = globalTreeMapService.findAllBy(globalTreeMapEntity);

        if(globalTreeMapEntityList == null || globalTreeMapEntityList.isEmpty()){
            return ResponseEntity.ok(CommonResponse.error("not found, thus not delete", HttpStatus.INTERNAL_SERVER_ERROR));
        }else if (globalTreeMapEntityList.size() > 1){
            return ResponseEntity.ok(CommonResponse.error("not found, thus not delete", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.delete(globalTreeMapEntityList.get(0))));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/getConnectInfo/pdService/pdServiceVersion/jiraProject.do"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getConnectInfo_pdService_pdServiceVersion_jiraProject(GlobalTreeMapEntity globalTreeMapEntity, ModelMap model, HttpServletRequest request) throws Exception {

        List<GlobalTreeMapEntity> savedList = globalTreeMapService.findAllBy(globalTreeMapEntity);

        List<GlobalTreeMapEntity> filteredList = savedList.stream().filter(savedData ->
                savedData.getPdservice_link() != null &&
                        savedData.getPdserviceversion_link() != null &&
                        savedData.getJiraproject_link() != null
        ).collect(Collectors.toList());
        return ResponseEntity.ok(CommonResponse.success(filteredList));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/setConnectInfo/pdService/pdServiceVersion/jiraProject.do"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?> setConnectInfo_pdService_pdServiceVersion_jiraProject(GlobalTreeMapEntity globalTreeMapEntity, ModelMap model, HttpServletRequest request) throws Exception {

        ParameterParser parser = new ParameterParser(request);
        String[] paramList = StringUtils.jsonStringifyConvert(parser.get("c_pdservice_jira_ids"));

        List<String> jiraProjectList = Arrays.stream(paramList).collect(Collectors.toList());

        // 1. pdService 와 pdServiceVersion 으로 리스트를 찾고
        // 2. 찾은 리스트를 중심으로 루프를 돌면서
        // 매치 리스트는 save 하고
        // non 매치 리스트는 delete 를 하자.

        GlobalTreeMapEntity searchObj = new GlobalTreeMapEntity();
        searchObj.setPdservice_link(globalTreeMapEntity.getPdservice_link());
        searchObj.setPdserviceversion_link(globalTreeMapEntity.getPdserviceversion_link());
        List<GlobalTreeMapEntity> searchedObjList = globalTreeMapService.findAllBy(searchObj);

        // 1. pdService 와 pdServiceVersion 으로 리스트를 찾고
        List<GlobalTreeMapEntity> filteredList = searchedObjList.stream().filter(data ->
                data.getPdservice_link() != null &&
                data.getPdserviceversion_link() != null &&
                data.getJiraproject_link() != null
        ).collect(Collectors.toList());

        // 2. 찾은 리스트를 중심으로 루프를 돌면서 삭제할거 삭제하고
        for(GlobalTreeMapEntity data : filteredList){
            Long jiraProjectLink = data.getJiraproject_link();
            boolean alreadyRegist = jiraProjectList.stream().anyMatch(dataObj -> jiraProjectLink == NumberUtils.toLong(dataObj) );

            if(alreadyRegist){
                //이미 등록된 데이터.
                log.info("already registered : getPdservice_link" + data.getPdservice_link());
                log.info("already registered : getPdserviceversion_link" + data.getPdserviceversion_link());
                log.info("already registered : getJiraproject_link" + data.getJiraproject_link());

                jiraProjectList.remove(data);

            }else {
                //등록은 되 있으나, 매치되지 않은 데이터 : 삭제 대상.
                globalTreeMapService.delete(data);
            }
        }

        for( String jiraProject : jiraProjectList ){

            GlobalTreeMapEntity saveTarget = new GlobalTreeMapEntity();
            saveTarget.setPdservice_link(globalTreeMapEntity.getPdservice_link());
            saveTarget.setPdserviceversion_link(globalTreeMapEntity.getPdserviceversion_link());
            saveTarget.setJiraproject_link(Long.parseLong(jiraProject));

            List<GlobalTreeMapEntity> checkList = globalTreeMapService.findAllBy(saveTarget);
            List<GlobalTreeMapEntity> checkDuplicate = checkList.stream().filter(data ->
                    data.getPdservice_link().equals(saveTarget.getPdservice_link()) &&
                            data.getPdserviceversion_link().equals(saveTarget.getPdserviceversion_link()) &&
                            data.getJiraproject_link().equals(saveTarget.getJiraproject_link())
            ).collect(Collectors.toList());

            if( checkDuplicate == null || checkDuplicate.isEmpty() == true) {
                globalTreeMapService.save(saveTarget);
            }else{
                log.info(globalTreeMapEntity.toString());
            }
        }

        return ResponseEntity.ok(CommonResponse.success("test"));

    }

}
