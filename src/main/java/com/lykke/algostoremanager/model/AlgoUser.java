package com.lykke.algostoremanager.model;

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
    private Collection<Algo> algos;

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
}