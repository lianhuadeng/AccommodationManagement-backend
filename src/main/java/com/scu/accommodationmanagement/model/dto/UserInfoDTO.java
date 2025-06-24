package com.scu.accommodationmanagement.model.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private String name;
    private Long userId;
    private String contact;
    private String location;

    public UserInfoDTO(String name, Long userId, String contact, String location) {
        this.name = name;
        this.userId = userId;
        this.contact = contact;
        this.location = location;
    }

}
