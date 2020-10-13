package com.example.tweeter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.StoryRequest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ServerFakeTest {
    ServerFake theFake;
    User theUser;
    @Before
    public void setUp() throws Exception {
        theFake = new ServerFake();
        LoginRequest req = new LoginRequest("18chiatt","1234");
        LoginResponse resp = theFake.login(req);
        theUser = resp.getLoggedInAs();


    }

    @Test
    public void getFollowing() {
        FollowerRequest req1 = new FollowerRequest(theUser,5,null);
        FollowerResponse resp1 =  theFake.getFollowers(req1);

        assert(resp1.hasMore());
        assert(resp1.getFollowers().size() == 5);

        FollowerRequest req = new FollowerRequest(theUser,10,resp1.getFollowers().get(0));
        FollowerResponse resp =  theFake.getFollowers(req);

        FollowerRequest req3 = new FollowerRequest(theUser,5,null);
        FollowerResponse resp3 = theFake.getFollowers(req3);
        assert(resp.hasMore());
        assert(resp.getFollowers().size() == 10);

        for(int i=0; i< 5; i++){
            assert(resp1.getFollowers().get(i).equals(resp.getFollowers().get(i)));
        }

        for(int i=0; i< 5; i++){
            assert(resp3.getFollowers().get(i).equals(resp.getFollowers().get(i)));
        }

        FollowerRequest req2 = new FollowerRequest(theUser,400,null);
        FollowerResponse resp2 =  theFake.getFollowers(req2);
        System.out.println(resp2.getFollowers().size());
        assert(resp2.getFollowers().size() < 400);
        assert(resp2.hasMore() == false);







    }

    @Test
    public void getFollowers() {
        FollowingRequest req = new FollowingRequest(theUser,5,null);
        FollowingResponse resp = theFake.getFollowing(req);
        assert(resp.hasMore());
        assert(resp.getUsersTheyAreFollowing().size() == 5);


        FollowingRequest req1 = new FollowingRequest(theUser,10,null);
        FollowingResponse resp1 = theFake.getFollowing(req1);

        FollowingRequest req2 = new FollowingRequest(theUser,5,resp.getUsersTheyAreFollowing().get(4));
        FollowingResponse resp2 = theFake.getFollowing(req2);

        for(int i =0; i< 5; i++){
            assert(resp.getUsersTheyAreFollowing().get(i).equals(resp1.getUsersTheyAreFollowing().get(i)));
        }

        for(int i=0; i< 5; i++){
            assert(resp2.getUsersTheyAreFollowing().get(i).equals(resp1.getUsersTheyAreFollowing().get(i+5)));
        }



    }

    @Test
    public void getStory(){
        StoryRequest req = new StoryRequest(5,theUser,null);
        StoryResponse resp = theFake.getStory(req);
        assert(resp.getTheStatus().size() == 5);

        for(Status s : resp.getTheStatus()){
            System.out.println(s.toString());
        }

        Status previousLast = theFake.getStory(req).getTheStatus().get(4);
        StoryRequest req2 = new StoryRequest(5,theUser,previousLast);
        StoryResponse resp1 = theFake.getStory(req2);

        for(Status s : resp1.getTheStatus()){
            System.out.println(s.toString());
        }

        assert(resp1.getTheStatus().contains(previousLast) == false);
        assert(resp1.getTheStatus().size() == 5);

    }

}