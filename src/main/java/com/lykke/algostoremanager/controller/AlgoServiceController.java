package com.lykke.algostoremanager.controller;

import com.lykke.algostoremanager.api.AlgoServiceManager;
import com.lykke.algostoremanager.exception.AlgoException;
import com.lykke.algostoremanager.exception.AlgoServiceManagerErrorCode;
import com.lykke.algostoremanager.model.Algo;
import com.lykke.algostoremanager.model.AlgoService;
import com.lykke.algostoremanager.model.AlgoServiceStatus;
import com.lykke.algostoremanager.repo.AlgoRepository;
import com.lykke.algostoremanager.repo.AlgoServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by niau on 11/7/17.
 */

@RestController
@RequestMapping("/algo/service")
@Transactional
public class AlgoServiceController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlgoServiceManager algoServiceManager;

    @Autowired
    AlgoRepository algoRepository;

    @Autowired
    AlgoServiceRepository algoServiceRepository;

    @RequestMapping(value = "/create", method= RequestMethod.PUT)

    public AlgoService createAlgoService(@RequestParam Long algoId,@RequestParam String name, @RequestParam String appKey){

        Algo algo = algoRepository.findById(algoId);
        AlgoService algoService = new AlgoService();


        if (algo!=null) {

            String repo = algo.getRepo();
            String tag = algo.getName();
            algoService.setAlgo(algo);
            String serviceId = algoServiceManager.createService(repo + ":" + tag, name, appKey);
            algoService.setServiceId(serviceId);
            algoService.setName(name);
            algoService.setStatus("CREATED");
            algoServiceRepository.save(algoService);

        }else {
            throw new AlgoException("Algo not found!!!", AlgoServiceManagerErrorCode.ALGO_NOT_FOUND);
        }
        return algoService;
    }

    @RequestMapping(value = "/{id}/delete", method= RequestMethod.POST)

    public void deleteAlgoService(@PathVariable Long id){

        AlgoService algoService = algoServiceRepository.findById(id);
        if (algoService!=null){
            String serviceId = algoService.getServiceId();

            algoServiceManager.deleteService(serviceId);
            algoService.setStatus(AlgoServiceStatus.REMOVED.toString());

        }else {
            throw new AlgoException("AlgoService not found!!!", AlgoServiceManagerErrorCode.ALGO_SERVICE_NOT_FOUND);
        }

    }

    @RequestMapping(value = "/{id}/status", method= RequestMethod.GET)


    public String getAlgoServiceStatus(@PathVariable Long id){

        AlgoService algoService = algoServiceRepository.findById(id);

        return algoService.getStatus();
    }


    @RequestMapping(value = "/{id}/update", method= RequestMethod.POST)
    //TODO does not work due to
    //https://github.com/docker-java/docker-java/pull/917

    public void updateAlgoService(@RequestParam Long buildId,@PathVariable Long id){

        AlgoService algoService = algoServiceRepository.findOne(id);

        if (algoService!=null) {
            Algo algo = algoRepository.findById(buildId);
            String repo = algo.getRepo();
            String tag = algo.getName();
            algoServiceManager.updateServiceWithNewAlgoVersion(repo + ":" + tag,algoService.getName(),algoService.getServiceId());
            algoService.setAlgo(algo);
            algoServiceRepository.save(algoService);
        }  else {
            throw new AlgoException("AlgoService not found!!!", AlgoServiceManagerErrorCode.ALGO_SERVICE_NOT_FOUND);
        }

    }


    @RequestMapping(value = "/{id}/get", method= RequestMethod.GET)

    public AlgoService get(@PathVariable Long id){

        AlgoService algoService = algoServiceRepository.findById(id);
        if (algoService!=null){
            return algoService;
        }else {
            throw new AlgoException("AlgoService not found!!!", AlgoServiceManagerErrorCode.ALGO_SERVICE_NOT_FOUND);
        }

    }

    @RequestMapping(value = "/get", method= RequestMethod.GET)

    public List<AlgoService> get(){

        List<AlgoService> algoServices = (List<AlgoService>) algoServiceRepository.findAll();

        if (algoServices!=null){
            return algoServices;
        }else {
            throw new AlgoException("AlgoServices not found!!!", AlgoServiceManagerErrorCode.ALGO_SERVICE_NOT_FOUND);
        }

    }


}