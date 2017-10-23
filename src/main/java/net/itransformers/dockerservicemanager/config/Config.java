package net.itransformers.dockerservicemanager.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by niau on 10/20/17.
 */

@Configuration
public class Config {
    @Bean
    public  DockerClientConfig createDockerClientConfig(){
        return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    }
     @Bean
     public   DockerCmdExecFactory createDockerCmdExecFactory (){
        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
            .withReadTimeout(10000)
            .withConnectTimeout(10000)
            .withMaxTotalConnections(100)
            .withMaxPerRouteConnections(10);
         return dockerCmdExecFactory;
     }
    @Bean
    public DockerClient   createDockerClient(DockerClientConfig dockerClientConfig, DockerCmdExecFactory dockerCmdExecFactory){

        DockerClient dockerClient = DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerCmdExecFactory(dockerCmdExecFactory).build();
        return  dockerClient;
    }
}
