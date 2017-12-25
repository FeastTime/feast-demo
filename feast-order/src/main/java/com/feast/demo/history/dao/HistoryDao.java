package com.feast.demo.history.dao;

import com.feast.demo.history.entity.History;
import com.feast.demo.user.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryDao  extends PagingAndSortingRepository<History,Long>,HistoryCustomDao{
}
