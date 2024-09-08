package com.mtks.request;

import com.mtks.PlantAPI;
import com.mtks.constants.UrlPrefix;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Plant {

    private final PlantAPI plantAPI;
    public Plant(PlantAPI plantAPI){
        this.plantAPI = plantAPI;
    }

    public CompletableFuture<String> getPlants(){
       return this.getPlants(1,"*");
    }

    public CompletableFuture<String> getPlants(int page){
        return this.getPlants(page,"*");
    }

    public CompletableFuture<String> getPlants(int page,String populate){
        return this.plantAPI.request("/api/plants")
                .setApiPrefix(UrlPrefix.CMS_V2)
                .addParam("pagination[page]",String.valueOf(page))
                .addParam("populate",populate)
                .execute();
    }



}
