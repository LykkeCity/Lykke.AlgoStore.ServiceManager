package com.lykke.algostoremanager.api;

/**
 * Created by cpt2nmi on 18.10.2017 г..
 */
public interface AlgoServiceManager {
    String createService(String buildId,String name);
    void deleteService(String serviceId);
    void updateServiceWithNewAlgoVersion(String newImageName ,String serviceName,String serviceId);

}
