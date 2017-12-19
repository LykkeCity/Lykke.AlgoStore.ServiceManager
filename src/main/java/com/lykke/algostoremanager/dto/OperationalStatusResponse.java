package com.lykke.algostoremanager.dto;

/**
 * Created by niau on 12/19/17.
 */
public class OperationalStatusResponse extends StatusResponse {

    public static String statusType ="OPERATIONAL";

    public OperationalStatusResponse(String status) {
        super(status);
    }
}
