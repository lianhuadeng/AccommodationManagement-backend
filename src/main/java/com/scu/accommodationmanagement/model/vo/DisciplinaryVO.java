package com.scu.accommodationmanagement.model.vo;

import lombok.Data;

@Data
public class DisciplinaryVO {
    Long disciplinaryId;
    Long dormitoryId;
    String dormitoryName;
    Long studentId;
    String studentName;
    String reason;
    String time;
    String location;
}
