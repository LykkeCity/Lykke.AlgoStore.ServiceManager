package com.lykke.algostoremanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by niau on 11/11/17.
 */

@Entity
@Table(name = "algo_test",

        indexes = {
                @Index(name = "containerId", columnList = "container_id", unique = true)}
)

public class AlgoTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="algo_test_id")
    private Long id;

    public AlgoTest() {}

    @Column(name="container_id")
    private String containerId;

    private String status;

    @OneToOne(optional = false)
    @JsonBackReference
    private Algo algo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Algo getAlgo() {
        return algo;
    }

    public void setAlgo(Algo algo) {
        this.algo = algo;
    }
}
