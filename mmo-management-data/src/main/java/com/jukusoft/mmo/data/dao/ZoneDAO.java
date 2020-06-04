package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.map.ZoneEntity;
import com.jukusoft.mmo.data.entity.realm.RealmEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneDAO extends PagingAndSortingRepository<ZoneEntity, Long> {

    public boolean existsByName(String name);

    public Optional<ZoneEntity> findByName(String name);

}
