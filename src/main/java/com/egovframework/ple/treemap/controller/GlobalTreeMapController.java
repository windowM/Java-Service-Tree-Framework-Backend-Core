package com.egovframework.ple.treemap.controller;

import com.arms.pdservice.model.PdServiceEntity;
import com.arms.pdservice.service.PdService;
import com.egovframework.ple.treeframework.controller.CommonResponse;
import com.egovframework.ple.treemap.model.GlobalTreeMap;
import com.egovframework.ple.treemap.model.GlobalTreeMapSpecification;
import com.egovframework.ple.treemap.service.GlobalTreeMapService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/arms/globaltreemap")
@RestController
@AllArgsConstructor
public class GlobalTreeMapController {

    private final GlobalTreeMapService globalTreeMapService;

    @ResponseBody
    @RequestMapping(
            value = {"/getAllGlobalTreeMap.do"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getAllGlobalTreeMap(GlobalTreeMap globalTreeMap, ModelMap model, HttpServletRequest request) throws Exception {

        Specification<GlobalTreeMap> spec = (root, query, criteriaBuilder) -> null;

        if (globalTreeMap.getMap_key() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalMap_key(globalTreeMap.getMap_key()));

        if (globalTreeMap.getFilerepository_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalFilerepository_link(globalTreeMap.getFilerepository_link()));

        if (globalTreeMap.getJiraconnectinfo_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalJiraconnectinfo_link(globalTreeMap.getJiraconnectinfo_link()));

        if (globalTreeMap.getJiraissue_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalJiraissue_link(globalTreeMap.getJiraissue_link()));

        if (globalTreeMap.getJiraissuepriority_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalJiraissuepriority_link(globalTreeMap.getJiraissuepriority_link()));

        if (globalTreeMap.getPdservice_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalPdservice_link(globalTreeMap.getPdservice_link()));

        if (globalTreeMap.getPdserviceversion_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalPdserviceversion_link(globalTreeMap.getPdserviceversion_link()));


        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.findAllBy(spec)));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/addGlobalTreeMap.do"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?> addGlobalTreeMap(GlobalTreeMap globalTreeMap, ModelMap model, HttpServletRequest request) throws Exception {

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.save(globalTreeMap)));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/alterGlobalTreeMap.do"},
            method = {RequestMethod.PUT}
    )
    public ResponseEntity<?> alterGlobalTreeMap(GlobalTreeMap globalTreeMap, ModelMap model, HttpServletRequest request) throws Exception {

        Specification<GlobalTreeMap> spec = (root, query, criteriaBuilder) -> null;

        if (globalTreeMap.getMap_key() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalMap_key(globalTreeMap.getMap_key()));

        List<GlobalTreeMap> globalTreeMapList = globalTreeMapService.findAllBy(spec);

        if(globalTreeMapList == null || globalTreeMapList.isEmpty()){
            return ResponseEntity.ok(CommonResponse.error("not found, thus not delete", HttpStatus.INTERNAL_SERVER_ERROR));
        }else if (globalTreeMapList.size() > 1){
            return ResponseEntity.ok(CommonResponse.error("not found, thus not delete", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        GlobalTreeMap searchedGlobalTreeMap = globalTreeMapList.get(0);

        searchedGlobalTreeMap.setFilerepository_link(globalTreeMap.getFilerepository_link());

        searchedGlobalTreeMap.setJiraconnectinfo_link(globalTreeMap.getJiraconnectinfo_link());
        searchedGlobalTreeMap.setJiraissue_link(globalTreeMap.getJiraissue_link());
        searchedGlobalTreeMap.setJiraissuepriority_link(globalTreeMap.getJiraissuepriority_link());

        searchedGlobalTreeMap.setPdservice_link(globalTreeMap.getPdservice_link());
        searchedGlobalTreeMap.setPdserviceversion_link(globalTreeMap.getPdserviceversion_link());

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.update(searchedGlobalTreeMap)));

    }

    @ResponseBody
    @RequestMapping(
            value = {"/removeGlobalTreeMap.do"},
            method = {RequestMethod.DELETE}
    )
    public ResponseEntity<?> removeGlobalTreeMap(GlobalTreeMap globalTreeMap, ModelMap model, HttpServletRequest request) throws Exception {

        Specification<GlobalTreeMap> spec = (root, query, criteriaBuilder) -> null;

        if (globalTreeMap.getMap_key() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalMap_key(globalTreeMap.getMap_key()));

        if (globalTreeMap.getFilerepository_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalFilerepository_link(globalTreeMap.getFilerepository_link()));

        if (globalTreeMap.getJiraconnectinfo_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalJiraconnectinfo_link(globalTreeMap.getJiraconnectinfo_link()));

        if (globalTreeMap.getJiraissue_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalJiraissue_link(globalTreeMap.getJiraissue_link()));

        if (globalTreeMap.getJiraissuepriority_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalJiraissuepriority_link(globalTreeMap.getJiraissuepriority_link()));

        if (globalTreeMap.getPdservice_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalPdservice_link(globalTreeMap.getPdservice_link()));

        if (globalTreeMap.getPdserviceversion_link() != null)
            spec = spec.and(GlobalTreeMapSpecification.equalPdserviceversion_link(globalTreeMap.getPdserviceversion_link()));

        List<GlobalTreeMap> globalTreeMapList = globalTreeMapService.findAllBy(spec);

        if(globalTreeMapList == null || globalTreeMapList.isEmpty()){
            return ResponseEntity.ok(CommonResponse.error("not found, thus not delete", HttpStatus.INTERNAL_SERVER_ERROR));
        }else if (globalTreeMapList.size() > 1){
            return ResponseEntity.ok(CommonResponse.error("not found, thus not delete", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return ResponseEntity.ok(CommonResponse.success(globalTreeMapService.delete(globalTreeMapList.get(0))));

    }

}
