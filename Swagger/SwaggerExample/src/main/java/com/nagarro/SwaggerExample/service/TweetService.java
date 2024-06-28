package com.nagarro.SwaggerExample.service;

import com.nagarro.SwaggerExample.entity.Tweet;

import java.util.List;

public interface TweetService {
    void addTweet();
    List<Tweet> getTweets();
}
