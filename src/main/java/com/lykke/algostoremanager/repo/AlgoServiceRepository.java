package com.lykke.algostoremanager.repo;

import com.lykke.algostoremanager.model.AlgoService;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by niau on 11/11/17.
 */
public interface AlgoServiceRepository extends CrudRepository<AlgoService, Long> {

    AlgoService findById(Long id);

}
