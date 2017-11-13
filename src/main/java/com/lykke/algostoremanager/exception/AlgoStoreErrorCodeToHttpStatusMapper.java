package com.lykke.algostoremanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niau on 10/26/17.
 */
@Component
public class AlgoStoreErrorCodeToHttpStatusMapper {

    protected Map<AlgoErrorCode, HttpStatus> errorCodeHttpStatusMap = new HashMap<>();

    public  AlgoStoreErrorCodeToHttpStatusMapper(){
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_COMPILATION_ERROR,HttpStatus.UNPROCESSABLE_ENTITY);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_CREATION_ERROR, HttpStatus.CREATED);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_NOT_FOUND, HttpStatus.NOT_FOUND);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_PULL_ERROR,HttpStatus.BAD_REQUEST);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_SAVE_ERROR,HttpStatus.BAD_REQUEST);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_SERVICE_START_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_TAG_ERROR,HttpStatus.BAD_REQUEST);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_TEST_ERROR,HttpStatus.NOT_ACCEPTABLE);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_TEST_ERROR,HttpStatus.NOT_FOUND);
        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.ALGO_ALREADY_EXISTS, HttpStatus.CONFLICT);

        errorCodeHttpStatusMap.put(AlgoServiceManagerErrorCode.INTERNAL_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public org.springframework.http.HttpStatus getHttpStatusFor(AlgoErrorCode algoErrorCode){
        HttpStatus httpStatus = errorCodeHttpStatusMap.get(algoErrorCode);
        if (httpStatus==null){
            throw new RuntimeException(String.format("Can't map Algo error code %s to HTTP status code",algoErrorCode.name()));
        }
        return httpStatus;
    }
}
