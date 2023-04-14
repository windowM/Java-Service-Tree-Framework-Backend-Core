package com.egovframework.ple.treemap.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@Builder
@Table(name = "GLOBAL_TREE_MAP")
@SelectBeforeUpdate(value=true)
@DynamicInsert(value=true)
@DynamicUpdate(value=true)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
public class GlobalTreeMap {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "map_key")
    private Long map_key;

    @Column(name = "filerepository_link")
    private Long filerepository_link;

    @Column(name = "jiraconnectinfo_link")
    private Long jiraconnectinfo_link;

    @Column(name = "jiraissue_link")
    private Long jiraissue_link;

    @Column(name = "jiraissuepriority_link")
    private Long jiraissuepriority_link;

    @Column(name = "pdservice_link")
    private Long pdservice_link;

    @Column(name = "pdserviceversion_link")
    private Long pdserviceversion_link;

}
