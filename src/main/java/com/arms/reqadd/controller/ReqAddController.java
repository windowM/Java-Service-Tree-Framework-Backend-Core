/*
 * @author Dongmin.lee
 * @since 2023-03-21
 * @version 23.03.21
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.reqadd.controller;

import com.egovframework.javaservice.treeframework.controller.TreeAbstractController;
import com.egovframework.javaservice.treeframework.interceptor.SessionUtil;
import com.egovframework.javaservice.treeframework.util.ParameterParser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

import com.arms.reqadd.model.ReqAddEntity;
import com.arms.reqadd.service.ReqAdd;
import org.hibernate.criterion.Order;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.egovframework.javaservice.treeframework.util.StringUtility.jsonStringifyConvert;

@Slf4j
@Controller
@RequestMapping(value = {"/arms/reqAdd"})
public class ReqAddController extends TreeAbstractController<ReqAdd, ReqAddEntity> {

    @Autowired
    @Qualifier("reqAdd")
    private ReqAdd reqAdd;

    @PostConstruct
    public void initialize() {
        setTreeService(reqAdd);
    }

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(
            value = {"/{changeReqTableName}/getMonitor.do"},
            method = {RequestMethod.GET}
    )
    public ModelAndView getMonitor(
            @PathVariable(value ="changeReqTableName") String changeReqTableName,
            ReqAddEntity reqAddEntity, ModelMap model, HttpServletRequest request) throws Exception {

        SessionUtil.setAttribute("getMonitor",changeReqTableName);

        reqAddEntity.setOrder(Order.asc("c_left"));
        List<ReqAddEntity> list = this.reqAdd.getChildNode(reqAddEntity);

        SessionUtil.removeAttribute("getMonitor");

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("result", list);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(
            value = {"/{changeReqTableName}/getChildNode.do"},
            method = {RequestMethod.GET}
    )
    public ModelAndView getSwitchDBChildNode(@PathVariable(value ="changeReqTableName") String changeReqTableName,
                         ReqAddEntity reqAddDTO, HttpServletRequest request) throws Exception {
        ParameterParser parser = new ParameterParser(request);
        if (parser.getInt("c_id") <= 0) {
            throw new RuntimeException();
        } else {

            SessionUtil.setAttribute("getChildNode",changeReqTableName);

            reqAddDTO.setWhere("c_parentid", new Long(parser.get("c_id")));
            List<ReqAddEntity> list = reqAdd.getChildNode(reqAddDTO);

            SessionUtil.removeAttribute("getChildNode");

            ModelAndView modelAndView = new ModelAndView("jsonView");
            modelAndView.addObject("result", list);
            return modelAndView;
        }
    }


}
