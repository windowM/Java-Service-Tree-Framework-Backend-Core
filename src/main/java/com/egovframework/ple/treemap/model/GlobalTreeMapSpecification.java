package com.egovframework.ple.treemap.model;

import org.springframework.data.jpa.domain.Specification;

public class GlobalTreeMapSpecification {

    public static Specification<GlobalTreeMap> equalMap_key(Long map_key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("map_key"), map_key);
    }

    public static Specification<GlobalTreeMap> equalFilerepository_link(Long filerepository_link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("filerepository_link"), filerepository_link);
    }

    public static Specification<GlobalTreeMap> equalJiraconnectinfo_link(Long jiraconnectinfo_link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("jiraconnectinfo_link"), jiraconnectinfo_link);
    }

    public static Specification<GlobalTreeMap> equalJiraissue_link(Long jiraissue_link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("jiraissue_link"), jiraissue_link);
    }

    public static Specification<GlobalTreeMap> equalJiraissuepriority_link(Long jiraissuepriority_link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("jiraissuepriority_link"), jiraissuepriority_link);
    }

    public static Specification<GlobalTreeMap> equalPdservice_link(Long pdservice_link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pdservice_link"), pdservice_link);
    }

    public static Specification<GlobalTreeMap> equalPdserviceversion_link(Long pdserviceversion_link) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pdserviceversion_link"), pdserviceversion_link);
    }

}
