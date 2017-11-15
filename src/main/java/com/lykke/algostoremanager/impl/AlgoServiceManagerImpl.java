package com.lykke.algostoremanager.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.Service;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.TaskSpec;
import com.google.common.collect.Lists;
import com.lykke.algostoremanager.api.AlgoServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niau on 10/23/17.
 */

@Component
public class AlgoServiceManagerImpl implements AlgoServiceManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    DockerClient dockerClient;


    void createAlgoService(String imageId,String name,String network,int numberOfCpus,Long memory){

        String cpus = Integer.toString(numberOfCpus);


//        dockerClient.initializeSwarmCmd(new SwarmSpec())
//                .withListenAddr("127.0.0.1")
//                .withAdvertiseAddr("127.0.0.1")
//                .exec();
//
//        dockerRule.getClient().createServiceCmd(new ServiceSpec()
//                .withName(SERVICE_NAME)
//                .withTaskTemplate(new TaskSpec()
//                        .withContainerSpec(new ContainerSpec()
//                                .withImage(DEFAULT_IMAGE))))
//                .exec();
//
//        List<Service> services = dockerClient.listServicesCmd()
//                .withNameFilter(Lists.newArrayList(SERVICE_NAME))
//                .exec();
//
//        assertThat(services, hasSize(1));
//
//        dockerRule.getClient().removeServiceCmd(SERVICE_NAME).exec();




//
//        CreateContainerResponse response = dockerClient
//                .createContainerCmd(imageId)
//                .withPrivileged(true)
//                .withName(name)
//                .withNetworkMode(network)
//                .withAliases(name)
//                .withBlkioWeight(300)
////                .withCpuShares(512)
////                .withCpuPeriod(100000)
//                .withCpusetCpus(cpus) // depends on env
//                .withMemory(268435456L)//                .withPortBindings(new PortBinding(
////                        Ports.Binding.bindIpAndPort("127.0.0.1", port),
////                        ExposedPort.tcp(2375)))
//                .exec();
//        String serviceId = dockerClient.startContainerCmd(response.getId()).exec();

    }

    @Override
    public String createService(String algoId, String name, String appKey) {

        ServiceSpec serviceSpec = new ServiceSpec();
        Map<String,String> labels = new HashMap<String,String>();

        labels.put("owner","algostoremanager");
        labels.put("name", name);
        ContainerSpec containerSpec = new ContainerSpec().withImage(algoId).withLabels(labels);

        TaskSpec taskSpec = new TaskSpec().withContainerSpec(containerSpec);

        serviceSpec.withName(name).withTaskTemplate(taskSpec);

        CreateServiceResponse response = dockerClient.createServiceCmd(serviceSpec).exec();



        return response.getId();
    }

    public List<Service> listServiceById(String serviceId) {

        return  dockerClient.listServicesCmd().withIdFilter(Lists.newArrayList(serviceId))
                .exec();


    }


    public List<Service> listServices() {

        return  dockerClient.listServicesCmd()
                .exec();


    }


    public List<Service> listServiceByName(String serviceName) {

        return  dockerClient.listServicesCmd()
                .withNameFilter(Lists.newArrayList(serviceName))
                .exec();


    }

    @Override
    public void deleteService(String serviceId) {

        dockerClient.removeServiceCmd(serviceId).exec();

    }

    @Override
    public void updateServiceWithNewAlgoVersion(String newImageId ,String serviceName,String serviceId) {
//
//        List<Service> services = listServices();
//
//        Service serviceForUpdate =null;
//        for (Service service : services) {
//            if (service.getSpec().getName().equals(serviceName)) {
//                serviceForUpdate = service;
//                break;
//            }
//
//        }
        ServiceSpec newServiceSpec = new ServiceSpec();
        Map<String,String> labels = new HashMap<String,String>();

        labels.put("owner","algostoremanager");
        labels.put("name", serviceName);
        ContainerSpec containerSpec = new ContainerSpec().withImage(newImageId).withLabels(labels);



        TaskSpec taskSpec = new TaskSpec().withContainerSpec(containerSpec);

        newServiceSpec.withName(serviceName).withTaskTemplate(taskSpec);
        taskSpec.withContainerSpec(containerSpec);
        newServiceSpec.withTaskTemplate(taskSpec).getUpdateConfig();

        dockerClient.updateServiceCmd(serviceId, newServiceSpec).withVersion(1L);

    }


}
