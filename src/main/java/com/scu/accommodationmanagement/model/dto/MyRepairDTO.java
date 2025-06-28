package com.scu.accommodationmanagement.model.dto;

import lombok.Data;

@Data
public class MyRepairDTO {
    private Long repairId;
    private String maintainerName;
    private String repairItem;
    private String status;
    private String applyTime;
    private String location;
    private String content;
    private String pictureUrl;
}
