package com.gToons.api.repository;

import com.gToons.api.model.UserDeckCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeckCardRepository extends JpaRepository<UserDeckCard, Long> {
    Optional<List<UserDeckCard>> findByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}
