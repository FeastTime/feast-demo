package com.feast.demo.version.dao;

import com.feast.demo.version.entity.Version;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface VersionDao extends PagingAndSortingRepository<Version,Long> {

    @Query(value = "select * from version where client_type = ?1 and version_number > ?2 ORDER BY version_number DESC LIMIT 1", nativeQuery = true)
    public Version findVersionNumberByClientTypeOrderByVersionNumberDesc(String clientType,Integer versionNumber);
}
