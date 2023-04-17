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
package com.arms.reqstatus.model;

import com.egovframework.ple.treeframework.model.TreeBaseEntity;
import com.egovframework.ple.treeframework.model.TreeSearchEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "T_ARMS_REQSTATUS")
@SelectBeforeUpdate(value=true)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class ReqStatusEntity extends TreeSearchEntity implements Serializable {

    public ReqStatusEntity() {
        super();
    }

    public ReqStatusEntity(Boolean copyBooleanValue) {
        super();
        this.copyBooleanValue = copyBooleanValue;
    }

 	@Override
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "c_id")
    public Long getC_id() {
        return super.getC_id();
    }
    //@Getter @Setter

    //-- 제품 서비스
    @Column(name = "c_pdservice_link")
    private Long c_pdservice_link;

    @Column(name = "c_pdservice_name")
    @Type(type="text")
    private String c_pdservice_name;

    //-- 제품 서비스 버전
    @Column(name = "c_pds_version_link")
    private Long c_version_link;

    @Column(name = "c_pds_version_name")
    @Type(type="text")
    private String c_pds_version_name;

    //-- 제품 서비스 연결 지라 프로젝트
    @Column(name = "c_jira_link")
    private Long c_jira_link;

    @Column(name = "c_jira_key")
    @Type(type="text")
    private String c_jira_key;

    @Column(name = "c_jira_url")
    @Type(type="text")
    private String c_jira_url;

    //-- 제품 서비스 연결 지라 프로젝트 버전
    @Column(name = "c_jira_version_link")
    private Long c_jira_version_link;

    @Column(name = "c_jira_version_name")
    @Type(type="text")
    private String c_jira_version_name;

    @Column(name = "c_jira_version_url")
    @Type(type="text")
    private String c_jira_version_url;

    //-- 요구사항
    @Column(name = "c_req_link")
    private Long c_req_link;

    @Column(name = "c_req_name")
    @Type(type="text")
    private String c_req_name;

    //-- 요구사항 우선 순위
    @Column(name = "c_req_priority_link")
    private Long c_req_priority_link;

    @Column(name = "c_req_priority_name")
    @Type(type="text")
    private String c_req_priority_name;

    //-- 요구사항 상태
    @Column(name = "c_req_status_link")
    private Long c_req_status_link;

    @Column(name = "c_req_status_name")
    @Type(type="text")
    private String c_req_status_name;

    //-- 요구사항 이슈
    @Column(name = "c_issue_link")
    private Long c_issue_link;

    @Column(name = "c_issue_key")
    @Type(type="text")
    private String c_issue_key;

    @Column(name = "c_issue_summery")
    @Type(type="text")
    private String c_issue_summery;

    @Column(name = "c_issue_url")
    @Type(type="text")
    private String c_issue_url;


    //-- 요구사항 이슈 우선순위
    @Column(name = "c_issue_priority_link")
    private Long c_issue_priority_link;

    @Column(name = "c_issue_priority_name")
    @Type(type="text")
    private String c_issue_priority_name;

    @Column(name = "c_issue_priority_url")
    @Type(type="text")
    private String c_issue_priority_url;


    //-- 요구사항 이슈 상태
    @Column(name = "c_issue_status_link")
    private Long c_issue_status_link;

    @Column(name = "c_issue_status_name")
    @Type(type="text")
    private String c_issue_status_name;

    @Column(name = "c_issue_status_url")
    @Type(type="text")
    private String c_issue_status_url;

    //-- 요구사항 이슈 해결책
    @Column(name = "c_issue_resolution_link")
    private Long c_issue_resolution_link;

    @Column(name = "c_issue_resolution_name")
    @Type(type="text")
    private String c_issue_resolution_name;

    @Column(name = "c_issue_resolution_url")
    @Type(type="text")
    private String c_issue_resolution_url;

    //-- 요구사항 리뷰 상황
    @Column(name = "c_req_review_status")
    @Type(type="text")
    private String c_req_review_status;

    //-- 요구사항 생성자
    @Column(name = "c_writer")
    @Type(type="text")
    private String c_writer;

    @Column(name = "c_create_date")
    private Date c_create_date;

    //-- 요구사항 이슈 연관, 서브 이슈 서머리
    @Column(name = "c_issue_link_issue_summary")
    @Type(type="text")
    private String c_issue_link_issue_summary;

    @Column(name = "c_issue_sub_issue_summary")
    @Type(type="text")
    private String c_issue_sub_issue_summary;

    //-- 기타
    @Column(name = "c_req_status_etc")
    @Type(type="text")
    private String c_req_status_etc;

    @Lob
    @Column(name = "c_req_status_contents")
    private String c_req_status_contents;

    /*
     * Extend Bean Field
     */
	@JsonIgnore
    private Boolean copyBooleanValue;

    @Transient
	@ApiModelProperty(hidden = true)
    public Boolean getCopyBooleanValue() {
        copyBooleanValue = false;
        if (this.getCopy() == 0) {
            copyBooleanValue = false;
        } else {
            copyBooleanValue = true;
        }
        return copyBooleanValue;
    }

    public void setCopyBooleanValue(Boolean copyBooleanValue) {
        this.copyBooleanValue = copyBooleanValue;
    }

    @Override
    public <T extends TreeSearchEntity> void setFieldFromNewInstance(T paramInstance) {
        if( paramInstance instanceof TreeBaseEntity){
            if(paramInstance.isCopied()) {
                this.setC_title("copy_" + this.getC_title());
            }
        }
    }
}
