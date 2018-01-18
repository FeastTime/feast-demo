package com.feast.demo.feedback.service.impl;

import com.feast.demo.feedback.dao.FeedbackDao;
import com.feast.demo.feedback.entity.Feedback;
import com.feast.demo.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    public void feedback(Feedback feedback) {
        feedbackDao.save(feedback);
    }
}
