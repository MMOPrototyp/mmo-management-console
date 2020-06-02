package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.setting.GlobalSettingEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingDAO extends PagingAndSortingRepository<GlobalSettingEntity, String> {

    //

}
