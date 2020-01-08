package com.gToons.api.repository;

import com.gToons.api.model.UserCollection;
import com.gToons.api.model.UserDeckCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
    Optional<List<UserCollection>> findByUserId(Integer userId);
}
