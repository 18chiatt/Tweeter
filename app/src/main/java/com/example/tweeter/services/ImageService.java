package com.example.tweeter.services;

import com.example.tweeter.model.domain.User;
import com.example.tweeter.presenter.util.ByteArrayUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageService {
    private static final String DEFAULT_URL = "https://chasehiattbucket.s3.amazonaws.com/pic.png";
    private static Map<String,byte[]> theCache = new HashMap<>();

    public static void loadImage(User toLoad){
        if(toLoad == null){
            return;
        }
        if(toLoad.getImageURL() == null){
            return;
        }
        if(toLoad.getImageBytes() != null){
            return;
        }


        try {
            if(theCache.containsKey(toLoad.getImageURL())){
                toLoad.setImageBytes(theCache.get(toLoad.getImageURL()));
                return;
            }
            System.out.println("Loading image from web");

            byte[] bytes = ByteArrayUtils.bytesFromUrl(toLoad.getImageURL());
            if(bytes == null || bytes.length < 20){
                bytes = ByteArrayUtils.bytesFromUrl(DEFAULT_URL);
            }
            theCache.put(toLoad.getImageURL(),bytes);
            toLoad.setImageBytes(bytes);
        } catch (IOException e) {
            System.out.println("UNABLE TO LOAD IMAGE!!!!!!!");
        }
    }

}
