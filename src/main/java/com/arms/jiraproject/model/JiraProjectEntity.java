/*
 * @author Dongmin.lee
 * @since 2023-03-19
 * @version 23.03.19
 * @see <pre>
 *  Copyright (C) 2007 by 313 DEV GRP, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by 313 developer group <313@313.co.kr>, December 2010
 * </pre>
 */
package com.arms.jiraproject.model;

import com.egovframework.ple.treeframework.model.TreeBaseEntity;
import com.egovframework.ple.treeframework.model.TreeSearchEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "T_ARMS_JIRAPROJECT")
@SelectBeforeUpdate(value=true)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "S_T_ARMS_JIRAPROJECT", sequenceName = "S_T_ARMS_JIRAPROJECT", allocationSize = 1)
public class JiraProjectEntity extends TreeSearchEntity implements Serializable {

    public JiraProjectEntity() {
        super();
    }

    public JiraProjectEntity(Boolean copyBooleanValue) {
        super();
        this.copyBooleanValue = copyBooleanValue;
    }

 	@Override
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="S_T_ARMS_JIRAPROJECT")
    @Column(name = "c_id")
    public Long getC_id() {
        return super.getC_id();
    }
    //@Getter @Setter

    //@Getter @Setter
    @Lob
    @Column(name="c_contents")
    private String c_contents;

    @Column(name="c_jira_link")
    private String c_jira_link;

    @Column(name="c_jira_id")
    private String c_jira_id;

    @Column(name="c_jira_key")
    private String c_jira_key;

    @Column(name="c_jira_name")
    private String c_jira_name;

    @Column(name="c_jira_avatar_48")
    private String c_jira_avatar_48;

    @Column(name="c_jira_avatar_32")
    private String c_jira_avatar_32;

    @Column(name="c_jira_avatar_24")
    private String c_jira_avatar_24;

    @Column(name="c_jira_avatar_16")
    private String c_jira_avatar_16;

    @Column(name="c_jira_category_link")
    private String c_jira_category_link;

    @Column(name="c_jira_category_id")
    private String c_jira_category_id;

    @Column(name="c_jira_category_name")
    private String c_jira_category_name;

    @Column(name="c_jira_category_desc")
    private String c_jira_category_desc;

    @Column(name="c_jira_con_user")
    private String c_jira_con_user;

    @Column(name="c_jira_con_pass")
    private String c_jira_con_pass;

    @Column(name="c_jira_con_token")
    private String c_jira_con_token;

    @Column(name="c_jira_con_jql")
    private String c_jira_con_jql;

    @Column(name="c_jira_con_passmode")
    private String c_jira_con_passmode;

    /*
     * Extend Bean Field
     */
    private Boolean copyBooleanValue;

    @Transient
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
