package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.realm.RealmEntity;
import com.jukusoft.mmo.data.entity.user.RoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RealmDAO extends PagingAndSortingRepository<RealmEntity, Long> {

    public boolean existsByName(String name);

    public Optional<RoleEntity> findByName(String name);

}
