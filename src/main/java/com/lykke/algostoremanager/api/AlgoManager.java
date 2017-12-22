package com.lykke.algostoremanager.api;

import com.lykke.algostoremanager.model.Algo;

/**
 * Created by niau on 11/12/17.
 */
public interface AlgoManager {

    public Algo get(Long id);
    public void remove(Long id);

}
