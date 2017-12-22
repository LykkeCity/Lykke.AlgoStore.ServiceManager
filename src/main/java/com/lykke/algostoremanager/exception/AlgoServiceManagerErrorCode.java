package com.lykke.algostoremanager.exception;

/**
 * Created by niau on 10/26/17.
 */
public enum AlgoServiceManagerErrorCode implements AlgoErrorCode {

    ALGO_CREATION_ERROR,
    ALGO_PULL_ERROR,
    ALGO_TAG_ERROR,
    INTERNAL_ERROR,
    ALGO_COMPILATION_ERROR,
    ALGO_TEST_ERROR,
    ALGO_NOT_FOUND,
    ALGO_SAVE_ERROR,
    ALGO_SERVICE_START_ERROR,
    ALGO_TEST_NOT_FOUND,
    UNSUPORTED_ALGO_TYPE,
    ALGO_ALREADY_EXISTS,
    ALGO_SERVICE_NOT_FOUND
}
