package com.scu.accommodationmanagement.model.vo;

import lombok.Data;

@Data
public class ChangePasswordVO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
