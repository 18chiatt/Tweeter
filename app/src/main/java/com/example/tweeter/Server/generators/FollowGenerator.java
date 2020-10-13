package com.example.tweeter.Server.generators;

import com.example.tweeter.model.domain.Follow;
import com.example.tweeter.model.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class FollowGenerator {
    private static Random random = new Random();
    private List<User> theUsers;

    public FollowGenerator(List<User> theUsers){
        this.theUsers = theUsers;
    }

    public List<Follow> generateFollows(User toGenerateOf, int number){
        Set<Integer> seen = new TreeSet<Integer>();
        List<Follow> toReturn =  new ArrayList<>();
        for (int i=0; i< number; i++){
            Integer toAddMaybe = Math.abs(random.nextInt() % theUsers.size());
            if(seen.contains(toAddMaybe) || theUsers.get(toAddMaybe).equals(toGenerateOf)){
                i--;
                continue;
            }
            seen.add(toAddMaybe);
            toReturn.add (new Follow(toGenerateOf, theUsers.get(toAddMaybe))) ;
        }
        return toReturn;
    }


    public List<Follow> makePeopleFollow(User newUser, int number) {
        List<Follow> toReturn = new ArrayList<>();
        Set<Integer> done = new TreeSet<>();
        for(int i=0; i< number; i++){
            Integer toAddMaybe = Math.abs(random.nextInt() % theUsers.size());
            if(done.contains(toAddMaybe) || theUsers.get(toAddMaybe).equals(newUser)){
                i--;
                continue;
            }
            done.add(toAddMaybe);
            Follow toAdd = new Follow(theUsers.get(toAddMaybe),newUser);
            toReturn.add(toAdd);

        }
        return toReturn;
    }
}
