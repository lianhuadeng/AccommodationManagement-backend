package com.scu.accommodationmanagement.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyApplicationDTO {
    private Long applicationId;
    private String applicationType;
    private String targetLocation;
    private String status;
    private String applicationTime;
    private String remark;
    private String opinion;
}
