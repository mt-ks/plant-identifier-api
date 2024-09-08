package com.mtks.request;

import com.mtks.PlantAPI;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ImageProcessNet  {
    private final PlantAPI plantAPI;
    public ImageProcessNet(PlantAPI plantAPI) {
        this.plantAPI = plantAPI;
    }


    public CompletableFuture<String> processImage(String imagePath) throws IOException {
        return this.plantAPI
                .request("/imageProcessNet/include-related-images=true&lang=en")
                .setImage(imagePath)
                .execute();
    }

}
