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
package com.arms.pdservice.controller;

import com.arms.dynamicdbmaker.service.DynamicDBMaker;
import com.arms.filerepository.model.FileRepositoryEntity;
import com.arms.filerepository.service.FileRepository;
import com.arms.pdservice.model.PdServiceEntity;
import com.arms.pdservice.service.PdService;
import com.arms.pdserviceversion.model.PdServiceVersionEntity;
import com.arms.pdserviceversion.service.PdServiceVersion;
import com.egovframework.ple.treeframework.controller.CommonResponse;
import com.egovframework.ple.treeframework.controller.TreeAbstractController;
import com.egovframework.ple.treeframework.util.*;
import com.egovframework.ple.treeframework.validation.group.AddNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RestController
@AllArgsConstructor
@RequestMapping(value = {"/arms/pdService"})
public class PdServiceController extends TreeAbstractController<PdService, PdServiceEntity> {

    @Autowired
    @Qualifier("pdService")
    private PdService pdService;

    @PostConstruct
    public void initialize() {
        setTreeService(pdService);
    }

    @ResponseBody
    @RequestMapping(
            value = {"/addPdServiceNode.do"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?> addPdServiceNode(@Validated({AddNode.class}) PdServiceEntity pdServiceEntity,
                                         BindingResult bindingResult, ModelMap model) throws Exception {
        log.info("PdServiceController :: addPdServiceNode");
        return ResponseEntity.ok(CommonResponse.success(pdService.addPdServiceAndVersion(pdServiceEntity)));
    }

    @RequestMapping(
            value = {"/addVersionToNode.do"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<?>  addVersionToNode(@RequestBody PdServiceEntity pdServiceEntity,
                                              BindingResult bindingResult, ModelMap model) throws Exception {

        return ResponseEntity.ok(CommonResponse.success(pdService.addPdServiceVersion(pdServiceEntity)));
    }

    @ResponseBody
    @RequestMapping(value = "/addEndNodeByRoot.do", method = RequestMethod.POST)
    public ResponseEntity<?> addEndNodeByRoot(@RequestBody PdServiceEntity pdServiceEntity,
                                         BindingResult bindingResult) throws Exception {
        return ResponseEntity.ok(CommonResponse.success(pdService.addNodeToEndPosition(pdServiceEntity)));
    }

    /**
     * 이미지 Upload를 처리한다.
     *
     * @param multiRequest
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/uploadFileToNode.do", method = RequestMethod.POST)
    public ModelAndView uploadFileToNode(final MultipartHttpServletRequest multiRequest,
                                         HttpServletRequest request, Model model) throws Exception {

        ParameterParser parser = new ParameterParser(request);
        long param_c_id = parser.getLong("pdservice_link");

        //return ResponseEntity.ok(CommonResponse.success(pdService.uploadFileTo(param_c_id, multiRequest)));
        HashMap<String, Set<FileRepositoryEntity>> map = new HashMap();

        map.put("files", pdService.uploadFileTo(param_c_id, multiRequest));
        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", map);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(
            value = {"/getPdServiceMonitor.do"},
            method = {RequestMethod.GET}
    )
    public ResponseEntity<?> getPdServiceMonitor(PdServiceEntity pdServiceEntity, ModelMap model, HttpServletRequest request) throws Exception {

        return ResponseEntity.ok(CommonResponse.success(pdService.getNodesWithoutRoot(pdServiceEntity)));

    }

    @RequestMapping(value="/removeVersion.do", method= RequestMethod.DELETE)
    public ModelAndView removeVersion(PdServiceVersionEntity treeSearchEntity, HttpServletRequest request) throws Exception {

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", pdService.removeVersionNode(treeSearchEntity));
        return modelAndView;
    }

}