package com.mtks;

import com.mtks.http.Client;
import com.mtks.request.Plant;


public class PlantAPI {

    public Plant plant;

    public PlantAPI(){
        this.plant = new Plant(this);
    }


    public Client request(String endpoint){
        return new Client(endpoint);
    }

}