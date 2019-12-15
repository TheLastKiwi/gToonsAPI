package com.gToons.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_deck", catalog = "gtoons")
public class UserDeckCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer userId;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer cardId;
}
