package com.lykke.algostoremanager.controller;

import com.lykke.algostoremanager.api.AlgoContainerManager;
import com.lykke.algostoremanager.exception.AlgoException;
import com.lykke.algostoremanager.exception.AlgoServiceManagerErrorCode;
import com.lykke.algostoremanager.model.Algo;
import com.lykke.algostoremanager.model.AlgoTest;
import com.lykke.algostoremanager.model.AlgoTestStatus;
import com.lykke.algostoremanager.repo.AlgoRepository;
import com.lykke.algostoremanager.repo.AlgoTestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by cpt2nmi on 16.10.2017 г..
 */


@RestController
@RequestMapping("/algo/test")

@Transactional

@CrossOrigin(origins = "http://localhost:4200")

public class AlgoTestController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlgoContainerManager algoContainerManager;

    @Autowired
    AlgoRepository algoRepository;

    @Autowired
    AlgoTestRepository algoTestRepository;




    @RequestMapping(value = "/get", method= RequestMethod.GET,produces = { "application/json" })

    public List<AlgoTest> getAll(){

        List<AlgoTest> algoTestList = (List<AlgoTest>) algoTestRepository.findAll();


        return algoTestList;
    }

    @RequestMapping(value = "/{id}/get", method= RequestMethod.GET,produces = { "application/json" })

    public AlgoTest get(@PathVariable Long id){

        AlgoTest algoTest = algoTestRepository.findById(id);


        return algoTest;
    }



    @RequestMapping(value = "/create", method= RequestMethod.PUT,produces = { "application/json" })

    public AlgoTest testAlgo(@RequestParam Long algoId,@RequestParam String name){
        Algo algo = algoRepository.findById(algoId);
        String containerId = null;
        String containerImageId = algo.getAlgoBuildImageId();

        AlgoTest algoTest = new AlgoTest();
        algoTest.setAlgo(algo);
        algoTest.setStatus(AlgoTestStatus.CREATED.toString());


        if (algo!=null) {
            containerId = algoContainerManager.create(containerImageId, name);
            algoTest.setContainerId(containerId);
            algoTestRepository.save(algoTest);
        } else {
            throw new AlgoException("Algo not found!!!", AlgoServiceManagerErrorCode.ALGO_NOT_FOUND);
        }
        return algoTest;
    }


    @RequestMapping(value = "/{id}/start", method= RequestMethod.PUT,produces = { "application/json" })

    public void start(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.start(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.RUNNING.toString());
            algoTestRepository.save(algotTest);

        }else{
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }


    }

    @RequestMapping(value = "/{id}/pause", method= RequestMethod.PUT,produces = { "application/json" })

    public void pauseTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.pause(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.PAUSED.toString());
            algoTestRepository.save(algotTest);
        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }


    }

    @RequestMapping(value = "/{id}/resume", method= RequestMethod.PUT,produces = { "application/json" })

    public void resumeTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.resume(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.RUNNING.toString());
            algoTestRepository.save(algotTest);

        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }


    }

    @RequestMapping(value = "/{id}/stop", method= RequestMethod.PUT,produces = { "application/json" })

    public void stopTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.stop(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.STOPPED.toString());
            algoTestRepository.save(algotTest);

        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }

    }

    @RequestMapping(value = "/{id}/getLog", method= RequestMethod.GET,produces = { "application/json" })

    public String getAlgoLog(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
            return algoContainerManager.getLog(algotTest.getContainerId());
        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }
    }

    @RequestMapping(value = "/{id}/getTailLog", method= RequestMethod.GET,produces = { "application/json" })

    public String getTailLog(@PathVariable Long id,@RequestParam int tail){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
            return algoContainerManager.getLog(algotTest.getContainerId(),tail);
        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }
    }

    @RequestMapping(value = "/{id}/status", method= RequestMethod.GET,produces = { "application/json" })

    public String getTestAlgoStatus(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
            return algoContainerManager.getStatus(algotTest.getContainerId());
        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }
    }

    @RequestMapping(value = "/{id}/delete", method= RequestMethod.DELETE,produces = { "application/json" })

    public void deleteTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
             algoContainerManager.delete(algotTest.getContainerId());
             algoTestRepository.delete(algotTest);
        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_ERROR);

        }
    }





}
