package com.lykke.algostoremanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by niau on 11/10/17.
 */
@Entity
@Table(name = "algoUser",
        indexes = {
                @Index(name = "userName", columnList = "user_name", unique = true)}
)

public class AlgoUser {
    @Id
    @Column(name="user_id")
    @GeneratedValue
    private Long userId;

    @Column(name="user_name",unique = true)

    private String userName;

    @OneToMany(mappedBy = "algoUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Collection<Algo> algos;


    @OneToMany(mappedBy = "algoUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Collection<AlgoTest> algoTest;

    @OneToMany(mappedBy = "algoUser", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Collection<AlgoService> algoService;

    protected AlgoUser() {
    }

    public AlgoUser(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<Algo> getAlgos() {
        return algos;
    }

    public void setAlgos(Collection<Algo> algos) {
        this.algos = algos;
    }

    public Collection<AlgoService> getAlgoService() {
        return algoService;
    }

    public void setAlgoService(Collection<AlgoService> algoService) {
        this.algoService = algoService;
    }

    public Collection<AlgoTest> getAlgoTest() {
        return algoTest;
    }

    public void setAlgoTest(Collection<AlgoTest> algoTest) {
        this.algoTest = algoTest;
    }

    @Override
    public String toString() {
        return userName;
    }
}