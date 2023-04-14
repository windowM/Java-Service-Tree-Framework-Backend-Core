package com.egovframework.ple.treemap.controller;

import com.egovframework.ple.treeframework.controller.CommonResponse;
import com.egovframework.ple.treemap.model.GlobalTreeMapEntity;
import com.egovframework.ple.treemap.model.GlobalTreeMapSpecification;
import com.egovframework.ple.treemap.service.GlobalTreeMapService;
import com.egovframework.ple.treemap.service.GlobalTreeMapServiceImpl;
import lombok.AllArgsConstructor;
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

}
