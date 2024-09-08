package com.mtks;

import com.mtks.http.Client;
import com.mtks.request.Diagnose;
import com.mtks.request.ImageProcessNet;
import com.mtks.request.Plant;


public class PlantAPI {

    public Plant plant;
    public ImageProcessNet imageProcessNet;
    public Diagnose diagnose;
    public PlantAPI(){
        this.plant = new Plant(this);
        this.imageProcessNet = new ImageProcessNet(this);
        this.diagnose = new Diagnose(this);
    }

    public Client request(String endpoint){
        return new Client(endpoint);
    }

}