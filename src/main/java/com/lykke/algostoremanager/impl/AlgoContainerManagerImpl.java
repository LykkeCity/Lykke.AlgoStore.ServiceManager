package com.lykke.algostoremanager.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.lykke.algostoremanager.api.AlgoContainerManager;
import com.lykke.algostoremanager.exception.AlgoException;
import com.lykke.algostoremanager.exception.AlgoServiceManagerErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value( "${docker.algo.mem:268435456}" )
    private Long dockerMemory;


    @Value( "${docker.algo.swap:268435456}" )
    private Long dockerSwap;



    @Override
    public String create(String imageId, String name) {
        CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
                .withName(name)
                .withMemory(dockerMemory)
                .withMemorySwap(dockerSwap)
                .exec();

        //  InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(container.getId()).exec();

        return container.getId();

    }


    @Override
    public String getLog(String id) {
        LogContainerResultCallback loggingCallback = new LogContainerResultCallback() {
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
    public String getLog(String id, int tail) {
        LogContainerResultCallback loggingCallback = new LogContainerResultCallback() {
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
                .withTail(tail)
                .exec(loggingCallback);

        try {
            loggingCallback.awaitCompletion(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return loggingCallback.toString();

    }

    @Override
    public void stop(String id) throws AlgoException {
        try {
            dockerClient.stopContainerCmd(id).exec();
        } catch (NotFoundException nte) {
            logger.error("Docker container with id: " + id + " not found!!!", nte);
            throw new AlgoException("AlgoTest not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);
        } catch (NotModifiedException nme) {
            logger.error("Docker container with id: " + id + " not modified!!!", nme);
            throw new AlgoException("AlgoTest not modified!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }

    }

    @Override
    public void start(String id) throws AlgoException {
        try {
            dockerClient.startContainerCmd(id).withContainerId(id).exec();
        } catch (NotFoundException nte) {
            logger.error("Docker container with id: " + id + " not found!!!", nte);
            throw new AlgoException("AlgoTest not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);
        } catch (NotModifiedException nme) {
            logger.error("Docker container with id: " + id + " not modified!!!", nme);
            throw new AlgoException("AlgoTest not modified!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }
    }

    @Override
    public void delete(String id) throws AlgoException {
        try {
            dockerClient.removeContainerCmd(id).withForce(true).exec();
        } catch (NotFoundException nte) {
            logger.error("Docker container with id: " + id + " not found!!!", nte);
            throw new AlgoException("AlgoTest not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);
        } catch (NotModifiedException nme) {
            logger.error("Docker container with id: " + id + " not modified!!!", nme);
            throw new AlgoException("AlgoTest not modified!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }

    }

    @Override
    public void pause(String id) throws AlgoException {
        try {
            dockerClient.pauseContainerCmd(id).exec();
        } catch (NotFoundException nte) {
            logger.error("Docker container with id: " + id + " not found!!!", nte);
            throw new AlgoException("AlgoTest not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);
        } catch (NotModifiedException nme) {
            logger.error("Docker container with id: " + id + " not modified!!!", nme);
            throw new AlgoException("AlgoTest not modified!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }


    }

    @Override
    public void resume(String id) throws AlgoException {
        try {
            dockerClient.unpauseContainerCmd(id).exec();
        } catch (NotFoundException nte) {
            logger.error("Docker container with id: " + id + " not found!!!", nte);
            throw new AlgoException("AlgoTest not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);
        } catch (NotModifiedException nme) {
            logger.error("Docker container with id: " + id + " not modified!!!", nme);
            throw new AlgoException("AlgoTest not modified!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }

    }

    @Override
    public String getStatus(String id) throws AlgoException {

        Map<String, String> filter = new HashMap<>();
        filter.put("id", id);
        List<Container> containers2 = dockerClient.listContainersCmd().withShowAll(true).exec();

        for (Container container : containers2) {
            //TODO make it more robust by comparing only the first 20 symbols
            if (container.getId().contains(id)) {
                return ContainerStatusMatcher.statusMatcher(container.getStatus());
            }
        }
        throw new AlgoException("AlgoTest not modified!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

    }


}
