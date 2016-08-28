package api.net;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Gladiator on 8/29/2016 AD.
 */
public class MultipartFormData {
    private String url;
    private HashMap<String, String> attributes = new HashMap<>();
    private HashMap<String, File> fileMap = new HashMap<>(1);

    private HttpPost httpPost;
    private CloseableHttpClient httpClient;


    public MultipartFormData() {
        this.httpClient = HttpClients.createDefault();
    }

    public MultipartFormData(String url, HashMap<String, String> attributes, HashMap<String, File> fileMap) {
        this();
        this.url = url;
        this.attributes = attributes;
        this.fileMap = fileMap;
    }

    public void initialize() {
        this.httpPost = new HttpPost(this.url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        this.attributes.forEach((key, value) -> builder.addTextBody(key, value, ContentType.TEXT_PLAIN));
        this.fileMap.forEach((key, value) -> {
            try {
                builder.addBinaryBody(
                        key,
                        new FileInputStream(value),
                        ContentType.APPLICATION_OCTET_STREAM,
                        value.getName()
                );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });

        HttpEntity multipart = builder.build();
        this.httpPost.setEntity(multipart);
    }

    public void send() throws IOException {
        // TODO: 8/29/2016 AD send over ssl.
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String, File> getFileMap() {
        return fileMap;
    }

    public void setFileMap(HashMap<String, File> fileMap) {
        this.fileMap = fileMap;
    }
}
