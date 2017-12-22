package com.lykke.algostoremanager.repo;

import com.lykke.algostoremanager.model.Algo;
import org.springframework.data.repository.CrudRepository;

public interface AlgoRepository extends CrudRepository<Algo, Long> {

    Algo findById(Long id);

    Algo findByNameAndVersion(String name, Long version);
}