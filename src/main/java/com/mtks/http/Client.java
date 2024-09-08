package com.mtks.http;

import com.mtks.constants.PlantAPIConstants;
import com.mtks.exceptions.RequestErrorException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Client {

    private final String endpoint;
    private final HashMap<String, String> params = new HashMap<>();
    private final HashMap<String, String> posts = new HashMap<>();
    private final HashMap<String, String> headers = new HashMap<>();

    public Client(String endpoint) {
        this.endpoint = endpoint;
        this.prepareRequest();
    }

    private void prepareRequest(){
        this.addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent","okhttp/4.9.1");
    }

    public Client addHeader(String key, String value){
        this.headers.put(key, value);
        return this;
    }

    public HashMap<String, String> getHeaders(){
        return this.headers;
    }

    public Client addParam(String key, String value){
        this.params.put(key, value);
        return this;
    }

    public Map<String, String> getParams(){
        return this.params;
    }

    public Client addPost(String key, String value){
        this.posts.put(key, value);
        return this;
    }

    public Map<String, String> getPosts(){
        return this.posts;
    }

    public String getUrl(){
        String requestUrl = PlantAPIConstants.BASE_URL + this.endpoint;
        if(!this.getParams().isEmpty()){
            requestUrl = requestUrl + "?" + RequestBodyBuilder.createQuery(this.getParams());
        }
        return requestUrl;
    }


    public CompletableFuture<String> execute(){

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder httpBuilder = HttpRequest.newBuilder();

            if(!this.getHeaders().isEmpty()){
                for (Map.Entry<String, String> headerItem : this.getHeaders().entrySet()){
                    httpBuilder.setHeader(headerItem.getKey(), headerItem.getValue());
                }
            }

            if(!this.getPosts().isEmpty()){
                String postData = RequestBodyBuilder.createQuery(this.getPosts());
                httpBuilder.POST(HttpRequest.BodyPublishers.ofString(postData));
            }


            HttpRequest httpRequestBuilder = httpBuilder
                    .uri(URI.create(this.getUrl()))
                    .build();

            return client.sendAsync(httpRequestBuilder, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);
        }catch (Exception e){
            throw new RequestErrorException(e.getMessage());
        }
    }



}
