package com.scu.accommodationmanagement.model.dto;

import lombok.Data;

@Data
public class MyApplicationDTO {
    private Long applicationId;
    private String applicationType;
    private Long applierId;
    private String applierName;
    private String leaderName;
    private String dormitoryAdminName;
    private String targetLocation;
    private String status;
    private String applicationTime;
    private String remark;
    private String opinion;
}
