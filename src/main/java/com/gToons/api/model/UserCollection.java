package com.gToons.api.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "user_collection", catalog = "gtoons")
public class UserCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer userId;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer cardId;
}
