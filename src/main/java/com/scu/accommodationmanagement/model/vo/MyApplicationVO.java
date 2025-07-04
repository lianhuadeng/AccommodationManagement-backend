package com.scu.accommodationmanagement.model.vo;

import lombok.Data;

@Data
public class MyApplicationVO {
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
