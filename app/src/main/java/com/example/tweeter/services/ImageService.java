package com.example.tweeter.services;

import com.example.tweeter.model.domain.User;
import com.example.tweeter.presenter.util.ByteArrayUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageService {
    private static Map<String,byte[]> theCache = new HashMap<>();

    public static void loadImage(User toLoad){
        if(toLoad.getImageBytes() != null){
            return;
        }
        try {
            if(theCache.containsKey(toLoad.getImageURL())){
                toLoad.setImageBytes(theCache.get(toLoad.getImageURL()));
            }

            byte[] bytes = ByteArrayUtils.bytesFromUrl(toLoad.getImageURL());
            theCache.put(toLoad.getImageURL(),bytes);
            toLoad.setImageBytes(bytes);
        } catch (IOException e) {
            System.out.println("UNABLE TO LOAD IMAGE!!!!!!!");
        }
    }

}
