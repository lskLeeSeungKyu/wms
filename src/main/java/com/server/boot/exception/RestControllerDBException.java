package com.server.boot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


/**
 * RestController에서 발생하는 DataAccessException 공통 예외 처리
 */
@RestControllerAdvice
@Slf4j
public class RestControllerDBException {

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public Map<String, String> DataAccessException(DataAccessException e) {
        Map<String, String> resultMap = new HashMap<>();

        String errorMessage = e.getMessage();
        Throwable cause = e.getCause();

        resultMap.put("result", "fail");
        resultMap.put("errorMessage", errorMessage);
        log.info("errorLog = {}", errorMessage);

        if(cause != null) {
            String errorCause = cause.getMessage();
            resultMap.put("errorCause", errorCause);
            log.info("errorLog = {}", errorCause);
        }

        return resultMap;
    }
}