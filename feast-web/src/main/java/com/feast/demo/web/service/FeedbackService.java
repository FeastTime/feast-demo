package com.feast.demo.web.service;

import com.feast.demo.feedback.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private com.feast.demo.feedback.service.FeedbackService feedbackRemoteService;

    public void feedback(Feedback feedback) {
        feedbackRemoteService.feedback(feedback);
    }
}
