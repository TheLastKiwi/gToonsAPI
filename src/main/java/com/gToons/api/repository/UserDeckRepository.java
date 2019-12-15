package com.gToons.api.repository;

import com.gToons.api.domain.UserDeckCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDeckRepository extends CrudRepository<UserDeckCard, Integer> {
        List<UserDeckCard> findAllById(int id);
}
