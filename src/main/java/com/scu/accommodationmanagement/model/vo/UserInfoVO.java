package com.scu.accommodationmanagement.model.vo;

import lombok.Data;

@Data
public class UserInfoVO {
    private String name;
    private Long userId;
    private String contact;
    private String location;
    private Long roomId;

    public UserInfoVO(String name, Long userId, String contact, String location, Long roomId) {
        this.name = name;
        this.userId = userId;
        this.contact = contact;
        this.location = location;
        this.roomId = roomId;
    }

    public UserInfoVO(String name, Long userId, String contact, String location) {
        this.name = name;
        this.userId = userId;
        this.contact = contact;
        this.location = location;
    }

}
