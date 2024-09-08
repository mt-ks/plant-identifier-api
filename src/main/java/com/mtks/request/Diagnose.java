package com.mtks.request;

import com.mtks.PlantAPI;
import com.mtks.constants.UrlPrefix;

import java.util.concurrent.CompletableFuture;

public class Diagnose {

    private PlantAPI plantAPI;
    public Diagnose(PlantAPI plantAPI) {
        this.plantAPI = plantAPI;
    }

    public CompletableFuture<String> getDiagnose(String query, int page) {
        return this.plantAPI.request("/api/diagnoses")
                .setApiPrefix(UrlPrefix.CMS_V2)
                .addParam("filters[name][$containsi]",query)
                .addParam("pagination[page]", String.valueOf(page))
                .addParam("pagination[pageSize]","20")
                .addParam("sort[0]", "name:asc")
                .addParam("populate[profile][populate]","images")
                .addParam("populate[category]", "title")
                .addParam("populate[thumbnail]", "formats")
                .addParam("locale","en")
                .execute();
    }

}
