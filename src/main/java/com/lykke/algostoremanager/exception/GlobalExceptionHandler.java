package com.lykke.algostoremanager.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @Autowired
    protected AlgoStoreErrorCodeToHttpStatusMapper algoErrorCodeToHttpStatusMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>handleRtpExceptions.</p>
     *
     * @param e a {@link AlgoException} object.
     * @return a {@link org.springframework.http.ResponseEntity} object.
     */
    @ExceptionHandler(AlgoException.class)
    public ResponseEntity<Map<String, String>> handleRtpExceptions(AlgoException e) {
        HttpStatus httpStatus = algoErrorCodeToHttpStatusMapper.getHttpStatusFor(e.getErrorCode());
        Map<String, String> body = new HashMap<>(2);
        body.put("code", e.getErrorCode().toString());
        body.put("message", e.getMessage());
        logger.error(e.getMessage(), e);
        return status(httpStatus).body(body);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonMappingException(JsonMappingException e) {
        Map<String, String> body = new HashMap<>(3);
        body.put("message", e.getMessage());
        body.put("path", e.getPath().toString());
        body.put("pathReference", e.getPathReference());
        logger.error(e.getMessage());
        return status(HttpStatus.BAD_REQUEST).body(body);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleJsonMappingException(MethodArgumentTypeMismatchException e) {
        Map<String, String> body = new HashMap<>(3);
        body.put("message", e.getMessage());
        body.put("name", e.getName());
        body.put("errorCode", e.getErrorCode());
        body.put("value", e.getValue().toString());
        body.put("propertyName", e.getPropertyName());
        body.put("parameterName", e.getParameter().getParameterName());
        logger.error(e.getMessage());
        return status(HttpStatus.BAD_REQUEST).body(body);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Map<String, String> body = new HashMap<>(3);
        body.put("message", e.getMessage());
        body.put("supportedHttpMethods", e.getSupportedHttpMethods().toString());
        logger.error(e.getMessage());
        return status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParameterException(MissingServletRequestParameterException e) {
        Map<String, String> body = new HashMap<>(2);
        body.put("message", e.getMessage());
        logger.error(e.getMessage());
        return status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * <p>handleAllOtherExceptions.</p>
     *
     * @param e a {@link java.lang.Exception} object.
     * @return a {@link org.springframework.http.ResponseEntity} object.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllOtherExceptions(Exception e) {
        Map<String, String> body = new HashMap<>(2);
        body.put("message", e.getMessage());
        logger.error(e.getMessage(), e);
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}