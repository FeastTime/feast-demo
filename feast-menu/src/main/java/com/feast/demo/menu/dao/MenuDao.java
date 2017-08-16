package com.feast.demo.menu.dao;

import com.feast.demo.menu.entity.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by aries on 2017/8/12.
 */
public interface MenuDao extends PagingAndSortingRepository<Menu,String>,MenuDaoCustom {

}
