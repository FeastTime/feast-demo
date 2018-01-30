package com.feast.demo.feedback.dao;

import com.feast.demo.feedback.entity.Feedback;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedbackDao extends PagingAndSortingRepository<Feedback,Long>{

}
