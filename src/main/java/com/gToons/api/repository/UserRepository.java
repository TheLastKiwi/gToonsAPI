package com.gToons.api.repository;

import com.gToons.api.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findTopByUsername(String username);

    User findTopByEmail(String email);

}
