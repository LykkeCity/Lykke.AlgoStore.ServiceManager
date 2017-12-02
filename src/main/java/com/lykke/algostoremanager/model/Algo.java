package com.lykke.algostoremanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;


@Entity
@Table(name = "algo",


        indexes = {
                @Index(name = "algoBuildImageId", columnList = "algo_build_image_id", unique = false)}

)

public class Algo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize(using=ToStringSerializer.class)
    @Column(name="algo_id")
    private Long id;

    @Column(name="algo_build_image_id")
    private String algoBuildImageId;
    private String name;
    private String repo;

    @Version
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long version;


    @ManyToOne(optional = false)
    @JsonManagedReference
    private AlgoUser algoUser;

    @OneToOne(mappedBy = "algo", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private AlgoTest algoTest;

    @OneToOne(mappedBy = "algo", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private AlgoService algoService;




    public Algo() {}

    public Algo(String algoBuildImageId, String name, String repo, AlgoUser algoUser) {
        this.algoBuildImageId = algoBuildImageId;
        this.name = name;
        this.repo = repo;
        this.algoUser = algoUser;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, algoBuildImageId='%s',name='%s',repo='%s]",
                id, algoBuildImageId, name,repo);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgoBuildImageId() {
        return algoBuildImageId;
    }

    public void setAlgoBuildImageId(String algoBuildImageId) {
        this.algoBuildImageId = algoBuildImageId;

    }

    public AlgoTest getAlgoTest() {
        return algoTest;
    }

    public void setAlgoTest(AlgoTest algoTest) {
        this.algoTest = algoTest;
    }

    public AlgoService getAlgoService() {
        return algoService;
    }

    public void setAlgoService(AlgoService algoService) {
        this.algoService = algoService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public AlgoUser getAlgoUser() {
        return algoUser;
    }

    public void setAlgoUser(AlgoUser algoUser) {
        this.algoUser = algoUser;
    }


    public Long getVersion() {
        return version;
    }




}