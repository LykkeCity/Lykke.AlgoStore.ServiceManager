package com.lykke.algostoremanager.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.lykke.algostoremanager.api.AlgoContainerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by niau on 10/23/17.
 */

@Component
public class AlgoContainerManagerImpl implements AlgoContainerManager {

    @Autowired
    DockerClient dockerClient;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public String create(String imageId, String name, String appKey){
    CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
            .withName(name)
            .withEnv(String.format("appKey==%s", appKey))
            .exec();

      //  InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(container.getId()).exec();

        return container.getId();

    }


    @Override
    public String getLog(String id) {
        LogContainerResultCallback loggingCallback = new LogContainerResultCallback(){
            protected final StringBuffer log = new StringBuffer();

            List<Frame> collectedFrames = new ArrayList<Frame>();

            boolean collectFrames = false;

            @Override
            public void onNext(Frame frame) {
                if (collectFrames) collectedFrames.add(frame);
                log.append(new String(frame.getPayload()));
            }

            @Override
            public String toString() {
                return log.toString();
            }


            public List<Frame> getCollectedFrames() {
                return collectedFrames;
            }

        };

        // this essentially test the since=0 case
        dockerClient.logContainerCmd(id)
                .withStdErr(true)
                .withStdOut(true)
                .withFollowStream(true)
                .withTailAll()
                .exec(loggingCallback);

        try {
            loggingCallback.awaitCompletion(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return loggingCallback.toString();

    }

    @Override
    public void stop(String id) {


        dockerClient.stopContainerCmd(id).exec();

    }

    @Override
    public void start(String id) {

        dockerClient.startContainerCmd(id).withContainerId(id).exec();


    }

    @Override
    public void delete(String id) {
        dockerClient.removeContainerCmd(id);

    }

    @Override
    public void pause(String id) {
        dockerClient.pauseContainerCmd(id).exec();


    }

    @Override
    public void resume(String id) {
        dockerClient.unpauseContainerCmd(id).exec();

    }

    @Override
    public String getStatus(String id) {

        Map<String,String> filter = new HashMap<>();
        filter.put("id",id);
        List<Container> containers2 = dockerClient.listContainersCmd().withShowAll(true).exec();

        for (Container container : containers2) {
            //TODO make it more robust by comparing only the first 20 symbols
            if (container.getId().contains(id)){
                return container.getStatus();
            }
        }
        return null;

    }


}
