package com.gToons.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_deck", catalog = "gtoons")
public class UserDeckCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer userId;

    @Column(columnDefinition = "INTEGER", nullable = false)
    private Integer cardId;

    public UserDeckCard(Integer userid, Integer cardId){
        this.userId=userid;
        this.cardId=cardId;
    }
}
