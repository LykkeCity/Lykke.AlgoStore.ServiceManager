package com.lykke.algostoremanager.controller;

import com.lykke.algostoremanager.api.AlgoContainerManager;
import com.lykke.algostoremanager.api.AlgoImageManager;
import com.lykke.algostoremanager.api.AlgoServiceManager;
import com.lykke.algostoremanager.exception.AlgoException;
import com.lykke.algostoremanager.exception.AlgoServiceManagerErrorCode;
import com.lykke.algostoremanager.model.Algo;
import com.lykke.algostoremanager.model.AlgoService;
import com.lykke.algostoremanager.model.AlgoTest;
import com.lykke.algostoremanager.repo.AlgoRepository;
import com.lykke.algostoremanager.repo.AlgoServiceRepository;
import com.lykke.algostoremanager.repo.AlgoTestRepository;
import com.lykke.algostoremanager.repo.AlgoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by niau on 11/12/17.
 */
@RestController
@RequestMapping("/algo")
@Transactional

@CrossOrigin(origins = "http://localhost:4200")

public class AlgoController {

    @Autowired
    AlgoServiceManager algoServiceManager;

    @Autowired
    AlgoServiceRepository algoServiceRepository;

    @Autowired
    AlgoImageManager algoImageManager;

    @Autowired
    AlgoUserRepository algoUserRepository;

    @Autowired
    AlgoRepository algoRepository;

    @Autowired
    AlgoTestRepository algoTestRepository;

    @Autowired
    AlgoContainerManager algoContainerManager;




    @RequestMapping(value = "/get", method= RequestMethod.GET)

    public List<Algo> getAlgo(){
        List<Algo> algos = (List<Algo>) algoRepository.findAll();

        if (algos!=null) {
            return algos;
        } else {
            throw new AlgoException("No algos found in the system!!!", AlgoServiceManagerErrorCode.ALGO_NOT_FOUND);

        }
    }


    @RequestMapping(value = "/{id}/get", method= RequestMethod.GET)

    public Algo getAlgo(@PathVariable Long id){
        Algo algo = algoRepository.findById(id);

        if (algo!=null) {
            return algo;
        } else {
            throw new AlgoException("Algo not found!!!", AlgoServiceManagerErrorCode.ALGO_NOT_FOUND);

        }
    }
    @RequestMapping(value = "/{id}/delete", method= RequestMethod.DELETE)

    public void removeALgo(@PathVariable Long id){
        Algo algo = algoRepository.findById(id);

        if (algo!=null) {

           AlgoService algoService =  algo.getAlgoService();

            if (algoService!=null){
                algoServiceManager.deleteService(algoService.getServiceId());
                algoServiceRepository.delete(algoService);
            }

           AlgoTest algoTest = algo.getAlgoTest();
           if (algoTest!=null){
              algoContainerManager.delete(algoTest.getContainerId());
              algoTestRepository.delete(algoTest);
           }
           algoImageManager.remove(algo.getAlgoBuildImageId());
           algoRepository.delete(algo);

        } else {
            throw new AlgoException("Algo not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }
    }

}
