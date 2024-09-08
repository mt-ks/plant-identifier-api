package com.mtks.http;

import com.mtks.constants.PlantAPIConstants;
import com.mtks.constants.UrlPrefix;
import com.mtks.exceptions.RequestErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

public class Client {

    private final String endpoint;
    private final HashMap<String, String> params = new HashMap<>();
    private final HashMap<String, String> posts = new HashMap<>();
    private final HashMap<String, String> headers = new HashMap<>();
    private String apiPrefix = null;
    private String imageSource = null;
    private String imageUUID = null;

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


    public Client setApiPrefix(String apiPrefix) {
        this.apiPrefix = apiPrefix;
        return this;
    }

    public String getApiPrefix() {
        String prefix = this.apiPrefix;
        if(prefix == null){
            return UrlPrefix.API;
        }
        return this.apiPrefix;
    }


    public String getUrl(){
        String requestUrl = PlantAPIConstants.REQUEST_PROTOCOL + this.getApiPrefix() + PlantAPIConstants.BASE_URL + this.endpoint;
        if(!this.getParams().isEmpty()){
            requestUrl = requestUrl + "?" + RequestBodyBuilder.createQuery(this.getParams());
        }
        return requestUrl;
    }

    public Client setImage(String imageFullPath) throws IOException {
        Path imagePath = Paths.get(imageFullPath);
        byte[] imageBytes = Files.readAllBytes(imagePath);

        this.imageUUID = UUID.randomUUID().toString();

        String delimiter = "--";
        String newLine = "\r\n";


        this.imageSource = delimiter + imageUUID + newLine +
                "content-disposition: form-data; name=\"organs\"" + newLine +
                "Content-Length: 4" + newLine + newLine +
                "auto" + newLine
                + delimiter + imageUUID + newLine +
                "content-disposition: form-data; name=\"images\"; filename=\"" + imageUUID +".JPEG\"" + newLine +
                "content-type: image/jpeg" + newLine +
                "Content-Length: " + new String(imageBytes) + newLine + newLine;

        return this;
    }


    public CompletableFuture<String> execute(){

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder httpBuilder = HttpRequest.newBuilder();

            if(!this.getHeaders().isEmpty()){
                for (Map.Entry<String, String> headerItem : this.getHeaders().entrySet()){
                    httpBuilder.header(headerItem.getKey(), headerItem.getValue());
                }
            }

            if(!this.getPosts().isEmpty()){
                String postData = RequestBodyBuilder.createQuery(this.getPosts());
                httpBuilder.POST(HttpRequest.BodyPublishers.ofString(postData));
            }

            if(this.imageSource != null){
                BodyPublisher bodyPublisher = BodyPublishers.ofString(this.imageSource);
                httpBuilder.setHeader("Content-Type", "multipart/form-data; boundary=" + this.imageUUID)
                        .POST(bodyPublisher);
            }


            HttpRequest httpRequestBuilder = httpBuilder
                    .uri(URI.create(this.getUrl()))
                    .build();

            System.out.println(httpRequestBuilder.headers().map());

            return client.sendAsync(httpRequestBuilder, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body);
        }catch (Exception e){
            throw new RequestErrorException(e.getMessage());
        }
    }



}
