package com.scu.accommodationmanagement.model.vo;

import lombok.Data;

@Data
public class MyRepairVO {
    private Long repairId;
    private String applierName;
    private String maintainerName;
    private String dormitoryName;
    private String repairItem;
    private String status;
    private String applyTime;
    private String location;
    private String content;
    private String pictureUrl;
}
