package com.arms.jiraissuepriority.model;

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
public class JiraIssuePriorityDTO extends TreeBaseDTO {


    private String c_issue_priority_id;

    private String c_issue_priority_desc;

    private String c_issue_priority_name;

    private String c_issue_priority_url;

}
