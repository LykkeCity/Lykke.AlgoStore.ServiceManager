package com.lykke.algostoremanager.model;

import javax.persistence.*;


@Entity
@Table(name = "algo",
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
    private String tag;
    private String repo;

    @ManyToOne(optional = false)
    private AlgoUser algoUser;

    @OneToOne(mappedBy = "algo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private AlgoTest algoTest;

    @OneToOne(mappedBy = "serviceAlgo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private AlgoService algoService;


    public Algo() {}

    public Algo(String algoBuildImageId, String tag, String repo, AlgoUser algoUser) {
        this.algoBuildImageId = algoBuildImageId;
        this.tag = tag;
        this.repo = repo;
        this.algoUser = algoUser;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, algoBuildImageId='%s',tag='%s',repo='%s]",
                id, algoBuildImageId,tag,repo);
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public AlgoTest getAlgoTests() {
        return algoTest;
    }

    public void setAlgoTests(AlgoTest algoTest) {
        this.algoTest = algoTest;
    }
}