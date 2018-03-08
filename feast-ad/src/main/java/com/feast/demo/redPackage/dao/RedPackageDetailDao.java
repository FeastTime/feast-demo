package com.feast.demo.redPackage.dao;

import com.feast.demo.redPackage.entity.RedPackageDetail;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;

public interface RedPackageDetailDao extends PagingAndSortingRepository<RedPackageDetail,Long>{

    ArrayList<RedPackageDetail> findByRedPackageId(String redPackageId);
}
