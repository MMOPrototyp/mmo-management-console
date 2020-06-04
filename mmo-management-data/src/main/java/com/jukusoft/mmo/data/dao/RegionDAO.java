package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.map.RegionEntity;
import com.jukusoft.mmo.data.entity.realm.RealmEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionDAO extends PagingAndSortingRepository<RegionEntity, Long> {

    public boolean existsByName(String name);

    public Optional<RegionEntity> findByName(String name);

}
