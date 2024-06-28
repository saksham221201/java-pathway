package com.nagarro.SwaggerExample.service.impl;

import com.nagarro.SwaggerExample.entity.Tweet;
import com.nagarro.SwaggerExample.service.TweetService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {
    private final List<Tweet> tweetList = new ArrayList<>();

    @Override
    public void addTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1);
        tweet.setTitle("My first tweet");
        tweet.setMsg("This is a dummy tweet for demonstration purposes.");
        tweetList.add(tweet);

        Tweet tweet2 = new Tweet();
        tweet2.setId(2);
        tweet2.setTitle("My second tweet");
        tweet2.setMsg("This is the second dummy tweet for demonstration purposes.");
        tweetList.add(tweet2);
    }

    @Override
    public List<Tweet> getTweets() {
        return tweetList;
    }
}
