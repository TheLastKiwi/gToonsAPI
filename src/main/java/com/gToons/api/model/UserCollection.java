package com.gToons.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_collection", catalog = "gtoons")
public class UserCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer userId;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer cardId;

    public UserCollection(Integer uid, Integer cid){
        userId = uid;
        cardId = cid;
    }
}
