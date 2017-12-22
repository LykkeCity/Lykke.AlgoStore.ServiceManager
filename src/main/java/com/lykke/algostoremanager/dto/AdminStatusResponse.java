package com.lykke.algostoremanager.dto;

/**
 * Created by niau on 12/19/17.
 */
public class AdminStatusResponse extends StatusResponse {

    public static String statusType ="ADMINISTRATIVE";

    public AdminStatusResponse(String status) {
        super(status);
    }
}
