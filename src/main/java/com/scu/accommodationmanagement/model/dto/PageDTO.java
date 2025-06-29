package com.scu.accommodationmanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T>{
    private Long total;//总条数
    private List<T> items;//当前页数据集合

}
