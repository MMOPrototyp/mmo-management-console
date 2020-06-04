package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.map.MapEntity;
import com.jukusoft.mmo.data.entity.realm.RealmEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapDAO extends PagingAndSortingRepository<MapEntity, Long> {

    public boolean existsByName(String name);

    public Optional<MapEntity> findByName(String name);

}
