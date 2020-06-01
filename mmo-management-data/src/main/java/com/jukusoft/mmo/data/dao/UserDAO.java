package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<UserEntity, Long> {

    public Optional<UserEntity> findOneByUsername(String username);

}
