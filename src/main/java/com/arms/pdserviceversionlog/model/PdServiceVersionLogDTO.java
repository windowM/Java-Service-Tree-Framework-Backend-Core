package com.arms.pdserviceversionlog.model;

import com.egovframework.javaservice.treeframework.model.TreeBaseDTO;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Lob;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PdServiceVersionLogDTO extends TreeBaseDTO {


    private Long c_pdservice_link;

    private String c_pds_version_start_date;

    private String c_pds_version_end_date;

    private String c_pds_version_contents;

    private String c_pds_version_etc;
}
