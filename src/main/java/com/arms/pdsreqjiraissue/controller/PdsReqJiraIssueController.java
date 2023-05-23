/*
 * @author Dongmin.lee
 * @since 2023-03-29
 * @version 23.03.29
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.pdsreqjiraissue.controller;

import com.arms.pdserviceversionlog.model.PdServiceVersionLogEntity;
import com.arms.pdsreqjiraissue.model.PdsReqJiraIssueDTO;
import com.egovframework.javaservice.treeframework.controller.TreeAbstractController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

import com.arms.pdsreqjiraissue.model.PdsReqJiraIssueEntity;
import com.arms.pdsreqjiraissue.service.PdsReqJiraIssue;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/pdsReqJiraIssue"})
public class PdsReqJiraIssueController extends TreeAbstractController<PdsReqJiraIssue, PdsReqJiraIssueDTO, PdsReqJiraIssueEntity> {

    @Autowired
    @Qualifier("pdsReqJiraIssue")
    private PdsReqJiraIssue pdsReqJiraIssue;

    @PostConstruct
    public void initialize() {
        setTreeService(pdsReqJiraIssue);
        setTreeEntity(PdsReqJiraIssueEntity.class);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
