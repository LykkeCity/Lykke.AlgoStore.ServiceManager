package com.lykke.algostoremanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by niau on 11/11/17.
 */

@Entity
@Table(name = "algo_service",
        indexes = {
                @Index(name = "serviceId", columnList = "service_id", unique = true)}
)

public class AlgoService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    public AlgoService() {}

    @Column(name="service_id")
    private String serviceId;

    private String status;

    @OneToOne(optional = false)
    @JsonBackReference
    private Algo serviceAlgo;


    @ManyToOne (optional = false)
    @JsonBackReference
    private AlgoUser algoUser;


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Algo getServiceAlgo() {
        return serviceAlgo;
    }

    public void setServiceAlgo(Algo serviceAlgo) {
        this.serviceAlgo = serviceAlgo;
    }

    public AlgoUser getAlgoUser() {
        return algoUser;
    }

    public void setAlgoUser(AlgoUser algoUser) {
        this.algoUser = algoUser;
    }
}
