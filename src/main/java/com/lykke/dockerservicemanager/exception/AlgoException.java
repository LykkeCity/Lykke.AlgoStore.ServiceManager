package com.lykke.dockerservicemanager.exception;

/**
 * Created by niau on 10/26/17.
 */
public class AlgoException extends RuntimeException {
    protected  AlgoErrorCode algoErrorCode;

    public AlgoException(AlgoErrorCode errorCode){
        super();
        this.algoErrorCode = errorCode;
    }

    public AlgoException(String message, AlgoErrorCode errorCode){
        super(message);
        this.algoErrorCode = errorCode;
    }


    public AlgoException(String message, Throwable cause, AlgoErrorCode errorCode){
        super(message,cause);
        this.algoErrorCode = errorCode;
    }


    public AlgoException(Throwable cause, AlgoErrorCode errorCode){
        super(cause);
        this.algoErrorCode = errorCode;
    }

    public AlgoErrorCode getErrorCode() {
        return algoErrorCode;
    }
}
