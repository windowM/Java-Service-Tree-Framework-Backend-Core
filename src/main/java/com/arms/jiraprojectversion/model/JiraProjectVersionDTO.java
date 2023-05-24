package com.arms.jiraprojectversion.model;

import com.egovframework.javaservice.treeframework.model.TreeBaseDTO;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JiraProjectVersionDTO extends TreeBaseDTO {


    private Long c_jira_link;

    private String c_jira_version_url;

    private String c_jira_version_id;

    private String c_jira_version_desc;

    private String c_jira_version_name;

    private String c_jira_version_projectid;

    private String c_jira_version_archived;

    private String c_jira_version_released;

    private String c_jira_version_releaseDate;

}
