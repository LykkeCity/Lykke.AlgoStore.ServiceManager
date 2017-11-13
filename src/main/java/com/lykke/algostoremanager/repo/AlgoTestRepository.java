package com.lykke.algostoremanager.repo;

import com.lykke.algostoremanager.model.AlgoTest;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by niau on 11/11/17.
 */
public interface AlgoTestRepository extends CrudRepository<AlgoTest, Long> {

    AlgoTest findById(Long id);

}
