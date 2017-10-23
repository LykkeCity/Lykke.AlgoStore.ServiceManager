package net.itransformers.dockerservicemanager.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.api.model.PushResponseItem;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import net.itransformers.dockerservicemanager.api.AlgoImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by niau on 10/23/17.
 */

@Component
public class AlgoImageManagerImpl implements AlgoImageManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    DockerClient dockerClient;

    @Override
    public String build(File dockerBaseDirPath, File dockerFilePath) {

        logger.debug("Building an image in + "+dockerBaseDirPath.getAbsolutePath()+ " with Dockerfile "+dockerFilePath.getAbsolutePath()+ "!");
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            @Override
            public void onNext(BuildResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }
            @Override
            public void onComplete() {
                logger.debug("completed!");
                super.onComplete();
            }
        };
        String imageId = dockerClient.buildImageCmd(dockerBaseDirPath).withDockerfile(dockerFilePath).exec(callback).awaitImageId();


        return imageId;
    }

    @Override
    public void pull(String repo, String tag) {
        logger.debug("Pulling an image from a repo: "+ repo+"with tag " +tag +"!");

        PullImageResultCallback callback = new PullImageResultCallback() {

            @Override
            public void onNext(PullResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }
            @Override
            public void onComplete() {
                logger.debug("completed!");
                super.onComplete();
            }

        };

        dockerClient.pullImageCmd(repo).exec(callback).awaitSuccess();
    }

    @Override
    public void tag(String image, String repo, String tag) {

        dockerClient.tagImageCmd(image,repo,tag).exec();


    }

    @Override
    public void push(String image, String repo, String tag) {
        logger.debug("Pushing an image "+image+" from a repo: "+ repo+"with tag " +tag +"!");


        PushImageResultCallback callback = new PushImageResultCallback() {


            @Override
            public void onNext(PushResponseItem item) {
                logger.debug("id:" + item.getId()  +" status: "+item.getStatus());
                super.onNext(item);
            }

        };
        try {
            dockerClient.pushImageCmd(image).withName(repo).withTag(tag).exec(callback).awaitCompletion(5, TimeUnit.MINUTES.MINUTES);
        } catch (InterruptedException e) {
            logger.error("Pull failed with",e);
        }
    }

    @Override
    public List<Image> getImages() {
        List<Image> images = dockerClient.listImagesCmd().exec();
        return images;

    }
}
