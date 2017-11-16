package com.lykke.algostoremanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;


@Entity
@Table(name = "algo",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"algo_user_user_id", "name","version"}),

        indexes = {
                @Index(name = "algoBuildImageId", columnList = "algo_build_image_id", unique = false)}

)

public class Algo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="algo_id")
    private Long id;

    @Column(name="algo_build_image_id")
    private String algoBuildImageId;
    private String name;
    private String repo;

    @Version
    private Long version;


    @ManyToOne(optional = false)
    @JsonBackReference
    private AlgoUser algoUser;




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

    public void setVersion(Long version) {
        this.version = version;
    }


    public Long getVersion() {
        return version;
    }


}