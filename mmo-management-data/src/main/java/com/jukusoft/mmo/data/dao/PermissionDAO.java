package com.jukusoft.mmo.data.dao;

import com.jukusoft.mmo.data.entity.user.PermissionEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDAO extends PagingAndSortingRepository<PermissionEntity, String> {

    //

}
