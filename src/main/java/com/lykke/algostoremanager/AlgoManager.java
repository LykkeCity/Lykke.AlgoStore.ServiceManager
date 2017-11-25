package com.lykke.algostoremanager;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.SwarmSpec;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@Configuration

public class AlgoManager {

	public static void main(String[] args) {

		SpringApplication.run(AlgoManager.class, args);

	}

	@Bean
	public DockerClientConfig createDockerClientConfig(){
		return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
	}
	@Bean
	public DockerCmdExecFactory createDockerCmdExecFactory (){
		DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
					.withReadTimeout(40000)
				.withConnectTimeout(40000)
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

	@Bean
	SwarmSpec createDockerSwarmSpec(){
		return new SwarmSpec();

	}



}
