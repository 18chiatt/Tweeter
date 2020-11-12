package com.example.tweeter.Server.generators;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class TweetGenerator {
    private static Random random = new Random(System.currentTimeMillis());
    private List<User> theUsers;
    private Instant curr;
    private int numTweets;
    private final int numWordsPerTweet = 10;
    private static WordGenerator generator = new WordGenerator();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TweetGenerator(List<User> toSet, int numTweetsPerUser){
        this.theUsers = toSet;
        this.numTweets = numTweetsPerUser;
        curr = Instant.now();
    }

    public SortedSet<Status> getTweets(User u){
        SortedSet<Status> toReturn = new TreeSet<>();
        for(int i = 0; i< numTweets; i++){
            Status toAdd = new Status(generateTweet(), generateTime().getEpochSecond(),u);
            toReturn.add(toAdd);

        }
        return toReturn;

    }

    private String generateTweet(){
        StringBuilder toReturn = new StringBuilder();
        toReturn.append( "@" +theUsers.get(Math.abs( random.nextInt()) % theUsers.size()).getUserName()+ " ");
        toReturn.append(generator.getWebsite() + " ");

        for(int i = 0; i< numWordsPerTweet; i++){
            toReturn.append(generator.getWord() + " ");
        }

        toReturn.append( "@" +theUsers.get(Math.abs( random.nextInt()) % theUsers.size()).getUserName()+ " ");
        return toReturn.toString();

    }


    private Instant generateTime(){
        int offset =  ( Math.abs(random.nextInt()) % 31622400 * 7);

        return curr.minus(offset, ChronoUnit.SECONDS );

    }

}
