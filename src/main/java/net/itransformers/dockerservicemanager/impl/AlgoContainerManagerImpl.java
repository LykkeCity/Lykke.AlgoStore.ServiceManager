package net.itransformers.dockerservicemanager.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import net.itransformers.dockerservicemanager.api.AlgoContainerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by niau on 10/23/17.
 */

@Component
public class AlgoContainerManagerImpl implements AlgoContainerManager {

    @Autowired
    DockerClient dockerClient;

    @Override
    public String create(String imageId, String name, String appKey){
    CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
            .withName(name)
            .withEnv(String.format("appKey==%s", appKey))
            .exec();

        InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(container.getId()).exec();



        if (inspectContainerResponse.getState().getRunning()){
            return container.getId();

        } else if (inspectContainerResponse.getState().getDead()){

            throw new RuntimeException("Can't run algo with "+imageId +"!!!");

        }

        return null;
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

        dockerClient.startContainerCmd(id).exec();


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
        //TODO Get container current status
       // dockerClient
        return null;
    }


}
