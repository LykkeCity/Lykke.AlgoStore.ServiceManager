package com.lykke.dockerservicemanager.controller;

import com.lykke.dockerservicemanager.api.AlgoImageManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by niau on 11/3/17.
 */
@RestController
@RequestMapping("/algo")

public class AlgoBuildController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlgoImageManager dockerImageManager;

    @RequestMapping(value = "/upload-java-source", method= RequestMethod.POST, consumes = "text/plain")

    public String buildAlgoImageFromSource(@RequestBody String appCode, @RequestParam String algoUser,@RequestParam String algoName,@RequestParam String algoVersion){

        String algoUserDirName = algoUser+"-"+algoName+"-"+algoVersion;
        File baseDirectory = new File("/tmp"+File.separator+algoUserDirName);
        if (!baseDirectory.exists()){
            baseDirectory.mkdir();
        }
        File dockerFile = null;
        dockerFile = preareAlgoBuildEnv(baseDirectory, "Dockerfile.java");
        copySourceAlgoFile(baseDirectory, "Main.java", appCode);
        String imageId = dockerImageManager.build(baseDirectory,dockerFile);
        String tag = algoUser+"-"+algoName+"-"+algoVersion;
        String repo = "hub.itransformers.net/algos";
        dockerImageManager.tag(imageId,repo, tag);
        dockerImageManager.push(imageId, algoUser, tag);
        return  imageId;

    }

    @RequestMapping(value="/upload-java-binary", method=RequestMethod.POST)
    public String buildAlgoImageFromBinary(@RequestParam String algoUser,@RequestParam String algoName,@RequestParam String algoVersion,
                             @RequestPart(required = true) MultipartFile file){

        String algoUserDirName = algoUser+"-"+algoName+"-"+algoVersion;
        File baseDirectory = new File("/tmp"+File.separator+algoUserDirName);
        if (!baseDirectory.exists()){
            baseDirectory.mkdir();
        }
        File dockerFile = null;

        dockerFile  = preareAlgoBuildEnv(baseDirectory, "Dockerfile.jar.java");
        copyBinaryAlgoFile(baseDirectory, "algo.jar", file);
        String imageId = dockerImageManager.build(baseDirectory,dockerFile);
        String tag = algoUser+"-"+algoName+"-"+algoVersion;
        String repo = "hub.itransformers.net/algos";
        dockerImageManager.tag(imageId,repo, tag);
        dockerImageManager.push(imageId, algoUser, tag);
        return  imageId;
    }


    private File preareAlgoBuildEnv(File baseDirectory, String dockerFileName){

        File dockerFileTemplate = null;
        File dockerFile = null;
        try {

            dockerFileTemplate = new File(".",dockerFileName);
            dockerFile = new File(baseDirectory,"Dockerfile");
            FileUtils.copyFile(dockerFileTemplate, dockerFile);

        } catch (IOException e) {
            logger.error("Can't copy "+ dockerFileTemplate.getAbsolutePath()  + " to "+dockerFile.getAbsolutePath());
        }

        return dockerFile;
    }

    private void copyBinaryAlgoFile(File baseDirectory, String fileName, MultipartFile file){

        File appFile = null;
        try {
            appFile = new File(baseDirectory,fileName);
            appFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(appFile);
            fos.write(file.getBytes());
            fos.close();

        } catch (IOException e) {
            logger.error("Can't write " + appFile + " to " + appFile.getAbsolutePath());
        }

    }


    private void copySourceAlgoFile(File baseDirectory, String fileName, String appCode){

        File appFile = null;
        try {
            appFile = new File(baseDirectory,fileName);
            appFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.writeStringToFile(appFile, appCode);

        } catch (IOException e) {
            logger.error("Can't write " + appCode + " to " + appFile.getAbsolutePath());
        }

    }

}
