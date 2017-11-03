package com.lykke.dockerservicemanager.controller;

import com.lykke.dockerservicemanager.api.AlgoContainerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cpt2nmi on 16.10.2017 г..
 */


@RestController
@RequestMapping("/algo/test")

public class AlgoTestController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlgoContainerManager algoContainerManager;




    @RequestMapping(value = "/create", method= RequestMethod.PUT)

    public String testAlgo(@RequestParam String algoInstanceId,@RequestParam String name, @RequestParam String appKey){
        String containerId = algoContainerManager.create(algoInstanceId,name, appKey);
        return containerId;

    }


    @RequestMapping(value = "/{id}/start", method= RequestMethod.PUT)

    public void start(@PathVariable String id){
        algoContainerManager.start(id);

    }

    @RequestMapping(value = "/{id}/pause", method= RequestMethod.PUT)

    public void pauseTestAlgo(@PathVariable String id){
         algoContainerManager.pause(id);

    }

    @RequestMapping(value = "/{id}/resume", method= RequestMethod.PUT)

    public void resumeTestAlgo(@PathVariable String id){
        algoContainerManager.resume(id);

    }

    @RequestMapping(value = "/{id}/stop", method= RequestMethod.PUT)

    public void stopTestAlgo(@RequestParam String id){
        algoContainerManager.stop(id);

    }

    @RequestMapping(value = "/{id}/getLog", method= RequestMethod.GET)

    public String getAlgoLog(String id){

        return    algoContainerManager.getLog(id);
    }

    @RequestMapping(value = "/{id}/status", method= RequestMethod.GET)

    public String getTestAlgoStatus(String id){

        return   algoContainerManager.getStatus(id);
    }


}
