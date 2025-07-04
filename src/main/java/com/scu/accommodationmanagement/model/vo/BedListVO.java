package com.scu.accommodationmanagement.model.vo;


import lombok.Data;

@Data
public class BedListVO {
    private Long parkId;
    private String parkName;
    private Long buildingId;
    private Long floor;
    private Long roomId;
    private Long bedId;
    private Long userId;
    private String userName;
}
