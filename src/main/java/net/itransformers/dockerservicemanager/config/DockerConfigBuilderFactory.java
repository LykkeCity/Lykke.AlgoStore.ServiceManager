package net.itransformers.dockerservicemanager.config;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;

/**
 * Created by cpt2nmi on 16.10.2017 г..
 */
public class DockerConfigBuilderFactory {

    public static  DockerClientConfig createDockerClientConfig(){
        return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    }
}
