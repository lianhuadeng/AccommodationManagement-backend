package com.scu.accommodationmanagement.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T>{
    private Integer code;//状态代码 1：失败，0：成功
    private String message;//提示信息
    private T data;//响应数据

    public static <E> Result<E> success(E data){
        return new Result<>(0,"操作成功",data);
    }
    public static Result success(){
        return new Result(0,"操作成功",null);
    }
    public static Result error(String message){
        return new Result(1,message,null);
    }
}
