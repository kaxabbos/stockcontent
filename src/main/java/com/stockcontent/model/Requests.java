package com.stockcontent.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Requests {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ads car;
    @OneToOne(fetch = FetchType.LAZY)
    private Users user;
    @Column(length = 5000)
    private String message;
    private String tel;

    public Requests(Ads car, Users user, String message, String tel) {
        this.car = car;
        this.user = user;
        this.message = message;
        this.tel = tel;
    }
}
