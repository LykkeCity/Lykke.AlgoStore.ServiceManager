package com.lykke.algostoremanager.repo;

import com.lykke.algostoremanager.model.AlgoUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by niau on 11/10/17.
 */
public interface AlgoUserRepository extends CrudRepository<AlgoUser,String> {
    AlgoUser findByUserName(String userName);

}
