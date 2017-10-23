package net.itransformers.dockerservicemanager.controller;

import net.itransformers.dockerservicemanager.api.AlgoContainerManager;
import net.itransformers.dockerservicemanager.api.AlgoImageManager;
import net.itransformers.dockerservicemanager.model.AlgoType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by cpt2nmi on 16.10.2017 г..
 */


@RestController
@RequestMapping("/algo")

public class AlgoController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlgoImageManager dockerImageManager;

    @Autowired
    AlgoContainerManager algoContainerManager;


    @RequestMapping(value = "/build", method= RequestMethod.POST)

    public String buildAlgoImage(@RequestBody String appCode, @RequestParam String algoType, @RequestParam String algoUser,@RequestParam String algoName,@RequestParam String algoVersion){

        String algoUserDirName = algoUser+"-"+algoName+"-"+algoVersion;
        File baseDirectory = new File("/tmp"+File.separator+algoUserDirName);
        if (!baseDirectory.exists()){
            baseDirectory.mkdir();
        }

        File dockerFileTemplate = null;
        File dockerFilePath = null;
        try {

            dockerFileTemplate = new File(".","DockerFile.java");
            dockerFilePath = new File(baseDirectory,"Dockerfile");
            FileUtils.copyFile(dockerFileTemplate, dockerFilePath);

        } catch (IOException e) {
            logger.error("Can't copy "+ dockerFileTemplate.getAbsolutePath()  + " to "+dockerFilePath.getAbsolutePath());
        }

        File appFile = null;
        if (algoType.equals(AlgoType.Java.toString())){
            try {
                appFile = new File(baseDirectory,"Main.java");
                appFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.writeStringToFile(appFile, appCode);

        } catch (IOException e) {
            logger.error("Can't write "+appCode+ " to "+appFile.getAbsolutePath());
        }

        String imageId = dockerImageManager.build(baseDirectory,dockerFilePath);
        String tag = algoUser+"-"+algoName+"-"+algoVersion;
        String repo = "hub.itransformers.net/algos";
        dockerImageManager.tag(imageId,repo, tag);
        dockerImageManager.push(imageId, algoUser, tag);
        return  imageId;

    }

    @RequestMapping(value = "/test", method= RequestMethod.PUT)

    public String testAlgo(@RequestParam String imageId,@RequestParam String name, @RequestParam String appKey){
        String containerId = algoContainerManager.create(imageId,name, appKey);
        algoContainerManager.start(containerId);
        return containerId;

    }

    @RequestMapping(value = "/getLog/{id}", method= RequestMethod.GET)

    public String getAlgoLog(String id){

        return    algoContainerManager.getLog(id);
    }



}
