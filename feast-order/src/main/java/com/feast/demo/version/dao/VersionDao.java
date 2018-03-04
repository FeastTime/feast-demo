package com.feast.demo.version.dao;

import com.feast.demo.version.entity.Version;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface VersionDao extends PagingAndSortingRepository<Version,Long> {

    @Query("select v.versionNumber from Version v where v.clientType = ?1 order by v.versionNumber desc ")
    public ArrayList<Integer> findVersionNumberByClientTypeOrderByVersionNumberDesc(String clientType, Pageable pageable);

    @Query("select v.downloadAddress from Version v where v.versionNumber = ?1")
    String findDownloadAddressByVersionNumber(Integer versionNumberMax);
}
