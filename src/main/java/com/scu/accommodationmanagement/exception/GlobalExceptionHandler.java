package com.scu.accommodationmanagement.exception;

import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public JsonResponse handleException(Exception e) {
        e.printStackTrace();
        return JsonResponse.failure(e.getMessage());
    }
}
