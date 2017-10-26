package com.lykke.dockerservicemanager;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Configuration
public class AlgoServiceManager {

	public static void main(String[] args) {

		SpringApplication.run(AlgoServiceManager.class, args);

	}

	@Bean
	public DockerClientConfig createDockerClientConfig(){
		return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
	}
	@Bean
	public DockerCmdExecFactory createDockerCmdExecFactory (){
		DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
				.withReadTimeout(10000)
				.withConnectTimeout(10000)
				.withMaxTotalConnections(100)
				.withMaxPerRouteConnections(10);
		return dockerCmdExecFactory;
	}
	@Bean
	public DockerClient createDockerClient(DockerClientConfig dockerClientConfig, DockerCmdExecFactory dockerCmdExecFactory){

		DockerClient dockerClient = DockerClientBuilder.getInstance(dockerClientConfig)
				.withDockerCmdExecFactory(dockerCmdExecFactory).build();
		return  dockerClient;
	}

}
