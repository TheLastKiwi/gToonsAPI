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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", catalog = "gtoons")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false, unique = true)
    private String username;

    @Column(columnDefinition = "VARCHAR(128)", nullable = false)
    private String email;

    @Column(name = "password_hash", columnDefinition = "CHAR(61)", nullable = false)
    private String passwordHash;

    @Override
    public String toString(){
        return id + ",\"" + username + "\",\"" + email + "\",\"" + passwordHash + "\"";
    }
}
