package com.lykke.dockerservicemanager.impl;

import com.github.dockerjava.api.DockerClient;
import com.lykke.dockerservicemanager.api.AlgoServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
