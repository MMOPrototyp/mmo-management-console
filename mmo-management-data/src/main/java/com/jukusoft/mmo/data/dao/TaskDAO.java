package com.jukusoft.mmo.data.dao;


import com.jukusoft.mmo.data.schedular.TaskEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDAO extends PagingAndSortingRepository<TaskEntity, String> {
}
