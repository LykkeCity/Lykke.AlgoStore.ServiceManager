package com.lykke.algostoremanager.controller;

import com.lykke.algostoremanager.api.AlgoImageManager;
import com.lykke.algostoremanager.model.Algo;
import com.lykke.algostoremanager.model.AlgoUser;
import com.lykke.algostoremanager.repo.AlgoRepository;
import com.lykke.algostoremanager.repo.AlgoUserRepository;
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
@RequestMapping("/algo/build")
@CrossOrigin(origins = "http://localhost:4200")


public class AlgoBuildController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlgoImageManager dockerImageManager;

    @Autowired
    AlgoUserRepository algoUserRepository;

    @Autowired
    AlgoRepository algoRepository;


    @RequestMapping(value = "/upload-java-source", method= RequestMethod.POST, consumes = "text/plain")



    public Long buildAlgoImageFromSource(@RequestBody String appCode, @RequestParam String algoUserName,@RequestParam String algoName,@RequestParam String algoVersion){

        String algoUserDirName = algoUserName+"-"+algoName+"-"+algoVersion;
        File baseDirectory = new File("/tmp"+File.separator+algoUserDirName);
        if (!baseDirectory.exists()){
            baseDirectory.mkdir();
        }
        File dockerFile = null;
        dockerFile = preareAlgoBuildEnv(baseDirectory, "Dockerfile.java");
        copySourceAlgoFile(baseDirectory, "Main.java", appCode);
        String imageId = dockerImageManager.build(baseDirectory, dockerFile);
        String tag = generateAlgoTag(algoUserName, algoName, algoVersion);
        String repo = "niau/algos";

        AlgoUser algoUser = algoUserRepository.findByUserName(algoUserName);
        if (algoUser==null){
          algoUser = createNewAlgoUser(algoUserName);
        }

        Algo algo = new Algo(imageId, tag,repo,algoUser);

        dockerImageManager.tag(imageId, repo, tag);
        dockerImageManager.push(imageId, repo, tag);

        algoRepository.save(algo);

        return  algo.getId();

    }

    @RequestMapping(value="/upload-java-binary", method=RequestMethod.POST)
    public Long buildAlgoImageFromBinary(@RequestParam String algoUserName,@RequestParam String algoName,@RequestParam String algoVersion,
                             @RequestPart(required = true) MultipartFile file){

        String algoUserDirName = algoUserName+"-"+algoName+"-"+algoVersion;
        File baseDirectory = new File("/tmp"+File.separator+algoUserDirName);
        if (!baseDirectory.exists()){
            baseDirectory.mkdir();
        }
        File dockerFile = null;

        dockerFile  = preareAlgoBuildEnv(baseDirectory, "Dockerfile.jar.java");
        copyBinaryAlgoFile(baseDirectory, "algo.jar", file);
        String imageId = dockerImageManager.build(baseDirectory, dockerFile);

        String tag = generateAlgoTag(algoUserName,algoName,algoVersion);
        String repo = "niau/algos";

        AlgoUser algoUser = algoUserRepository.findByUserName(algoUserName);
        if (algoUser==null){
            algoUser = createNewAlgoUser(algoUserName);
        }

        Algo algo = new Algo();
        System.out.println(algo.getId());

        algo.setTag(tag);
        algo.setRepo(repo);
        algo.setAlgoUser(algoUser);
        algo.setAlgoBuildImageId(imageId);


        algoRepository.save(algo);
        dockerImageManager.tag(imageId, repo, tag);
        dockerImageManager.push(imageId, repo, tag);

        return  algo.getId();
    }

    private AlgoUser createNewAlgoUser(String userName){
        AlgoUser algoUser = new AlgoUser(userName);
        algoUserRepository.save(algoUser);
        return algoUser;
    }

    private String generateAlgoTag(String algoUser,String algoName,String algoVersion){
         return   algoUser+"-"+algoName+"-"+algoVersion;
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
            logger.error("Can't create appFile: "+appFile.getAbsolutePath());
        }
        try {
            FileUtils.writeStringToFile(appFile, appCode);

        } catch (IOException e) {
            logger.error("Can't write " + appCode + " to " + appFile.getAbsolutePath());
        }

    }

}
