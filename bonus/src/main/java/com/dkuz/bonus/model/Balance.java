package com.dkuz.bonus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @OneToOne
    @Column(nullable = false)
    private User user;

    @Column
    private Integer count;

    public Balance(User user, Integer count) {
        this.user = user;
        this.count = count;
    }
}
