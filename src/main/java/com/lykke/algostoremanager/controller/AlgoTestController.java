package com.lykke.algostoremanager.controller;

import com.lykke.algostoremanager.api.AlgoContainerManager;
import com.lykke.algostoremanager.dto.AdminStatusResponse;
import com.lykke.algostoremanager.dto.LogResponse;
import com.lykke.algostoremanager.dto.OperationalStatusResponse;
import com.lykke.algostoremanager.exception.AlgoException;
import com.lykke.algostoremanager.exception.AlgoServiceManagerErrorCode;
import com.lykke.algostoremanager.model.Algo;
import com.lykke.algostoremanager.model.AlgoTest;
import com.lykke.algostoremanager.model.AlgoTestStatus;
import com.lykke.algostoremanager.model.ContainerStatus;
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
        if (algoTest!=null) {


            return algoTest;
        }
        else{
            throw new AlgoException("Algo not found!!!", AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }
    }



    @RequestMapping(value = "/create", method= RequestMethod.POST,produces = { "application/json" } )

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


    @RequestMapping(value = "/{id}/start", method= RequestMethod.PUT)

    public void start(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.start(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.RUNNING.toString());
            algoTestRepository.save(algotTest);

        }else{
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }


    }

    @RequestMapping(value = "/{id}/pause", method= RequestMethod.PUT)

    public void pauseTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.pause(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.PAUSED.toString());
            algoTestRepository.save(algotTest);
        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }


    }

    @RequestMapping(value = "/{id}/resume", method= RequestMethod.PUT)

    public void resumeTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {

            algoContainerManager.resume(algotTest.getContainerId());
            algotTest.setStatus(AlgoTestStatus.RUNNING.toString());
            algoTestRepository.save(algotTest);

        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }


    }

    @RequestMapping(value = "/{id}/stop", method= RequestMethod.PUT,produces={"application/json"})

    public void stopTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);


        if (algotTest!=null) {

            String operationalStatus =  algoContainerManager.getStatus(algotTest.getContainerId());
            logger.debug("Stopping a container with id :"+ algotTest.getContainerId()+" and operational status "+operationalStatus +"!!!");
            if (operationalStatus.toUpperCase().equals(ContainerStatus.status_running.toString())||
                    operationalStatus.toUpperCase().contains(ContainerStatus.status_up.toString()) ||
                    operationalStatus.toUpperCase().contains(ContainerStatus.status_paused.toString())) {

                algoContainerManager.stop(algotTest.getContainerId());
                algotTest.setStatus(AlgoTestStatus.STOPPED.toString());
                algoTestRepository.save(algotTest);
            }else {
                new AlgoException("Can't stop algotest with id "+ algotTest.getContainerId()+" !!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);
            }

        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }

    }

    @RequestMapping(value = "/{id}/getLog", method= RequestMethod.GET,produces={"application/json"})

    @ResponseBody
    public LogResponse getAlgoLog(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
            LogResponse logResponse = new LogResponse(algoContainerManager.getLog(algotTest.getContainerId(),1000));
            return logResponse;
        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }
    }

    @RequestMapping(value = "/{id}/getTailLog", method= RequestMethod.GET,produces={"application/json"})

    public LogResponse getTailLog(@PathVariable Long id,@RequestParam int tail){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
            LogResponse logResponse = new LogResponse(algoContainerManager.getLog(algotTest.getContainerId(),tail));
            return logResponse;
        } else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }
    }

    @RequestMapping(value = "/{id}/getAdministrativeStatus", method= RequestMethod.GET,produces={"application/json"})

    public AdminStatusResponse getAdministrativeStatus(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
            AdminStatusResponse adminStatusResponse = new AdminStatusResponse(algotTest.getStatus());
            return adminStatusResponse;

        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }
    }


    @RequestMapping(value = "/{id}/getOperationalStatus", method= RequestMethod.GET, produces={"application/json"})

    public OperationalStatusResponse getOperationalStatus(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);


        if (algotTest!=null) {
            OperationalStatusResponse operationalStatusResponse = new OperationalStatusResponse(algoContainerManager.getStatus(algotTest.getContainerId()));

            return operationalStatusResponse;
        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }
    }

    @RequestMapping(value = "/{id}/delete", method= RequestMethod.DELETE,produces={"application/json"})

    public void deleteTestAlgo(@PathVariable Long id){
        AlgoTest algotTest = algoTestRepository.findById(id);

        if (algotTest!=null) {
             algoContainerManager.delete(algotTest.getContainerId());
             algoTestRepository.delete(algotTest);
        }else {
            throw new AlgoException("AlgoTest not found!!!",AlgoServiceManagerErrorCode.ALGO_TEST_NOT_FOUND);

        }
    }





}
