package com.lykke.algostoremanager.impl;

import com.lykke.algostoremanager.exception.AlgoException;
import com.lykke.algostoremanager.exception.AlgoServiceManagerErrorCode;
import com.lykke.algostoremanager.model.ContainerStatus;

/**
 * Created by niau on 12/19/17.
 */
public class ContainerStatusMatcher {

    public static String statusMatcher(String currentStatus) throws AlgoException {
        for (ContainerStatus status : ContainerStatus.values()) {
            if (currentStatus.toUpperCase().contains(status.toString())) {
                return status.toString();
            }
        }
         throw new AlgoException("Algo status not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

    }
}
