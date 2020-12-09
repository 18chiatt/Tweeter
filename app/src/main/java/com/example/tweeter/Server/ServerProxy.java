package com.example.tweeter.Server;

import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.model.request.UserStatsRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ServerProxy implements ServerFacade { //do the requesting/decoding exClap
    private static List<ModelObserver> toNotify = new ArrayList<>();
    public final static String urlPath = "https://ah9dzl9fkd.execute-api.us-east-1.amazonaws.com/Test";
    private final static Gson gson = new GsonBuilder().create();

    @Override
    public FollowingResponse getFollowing(FollowingRequest req) {
        String path = "/Following";
        String response = sendAsJSON(req,path);
        return gson.fromJson(response,FollowingResponse.class);
    }

    @Override
    public FollowerResponse getFollowers(FollowerRequest req) {
        String path = "/Follower";
        String response = sendAsJSON(req,path);

        return gson.fromJson(response,FollowerResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        String path = "/Login";
        String response = sendAsJSON(req,path);
        return gson.fromJson(response,LoginResponse.class);

    }

    @Override
    public UserStatsResponse getUserStats(UserStatsRequest req) {
        String path = "/UserStats";
        String response = sendAsJSON(req,path);

        return gson.fromJson(response,UserStatsResponse.class);
    }

    @Override
    public StoryResponse getStory(StoryRequest req) {
        String path = "/Story";
        String response = sendAsJSON(req,path);

        return gson.fromJson(response,StoryResponse.class);
    }

    @Override
    public UserResponse getUser(UserRequest req) {
        String path = "/User";
        String response = sendAsJSON(req,path);
        return gson.fromJson(response, UserResponse.class);


    }

    @Override
    public FeedResponse getFeed(FeedRequest req) {
        if(req.getPreviousLast() != null) {
            System.out.println(req.getPreviousLast().toString() + " was previous!");
        } else {
            System.out.println("No previous!");
        }
        String path = "/Feed";
        String response = sendAsJSON(req,path);
        return gson.fromJson(response,FeedResponse.class);


    }

    @Override
    public FollowingStatusResponse getFollowingStatus(FollowingStatusRequest req) {
        String path = "/FollowingStatus";
        String response = sendAsJSON(req,path);
        return gson.fromJson(response,FollowingStatusResponse.class);


    }

    @Override
    public FollowManipulationResult manipulateFollow(FollowManipulationRequest req) {
        String path = "/FollowManipulation";
        String response = sendAsJSON(req,path);
        FollowManipulationResult followManipulationResult = gson.fromJson(response, FollowManipulationResult.class);
        followManipulationResult.setToUpdate(toNotify);
        return followManipulationResult;


    }

    @Override
    public PostStatusResponse postStatus(PostStatusRequest req) {
        String path = "/PostStatus";
        System.out.println(gson.toJson(req));
        String response = sendAsJSON(req,path);

        PostStatusResponse resp = gson.fromJson(response,PostStatusResponse.class);
        resp.setToUpdate(toNotify);
        return resp;

    }

    @Override
    public RegisterResponse registerUser(RegisterRequest req) {
        String path = "/Register";
        String response = sendAsJSON(req,path);
        return gson.fromJson(response,RegisterResponse.class);
    }

    @Override
    public void registerObserver(ModelObserver obs) {
        toNotify.add(obs);

    }

    private String sendAsJSON(Object o, String toSendTo){
        try {
            URL url = new URL((urlPath + toSendTo));
            System.out.println(url.toString());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String stringJSON = gson.toJson(o);
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = stringJSON.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                return response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
