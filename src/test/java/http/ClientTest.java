package http;

import com.mtks.constants.PlantAPIConstants;
import com.mtks.http.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ClientTest {

    private Client client;
    private final String testEndpoint = "/test";
    @BeforeEach
    public void setUp() {
        this.client = new Client(this.testEndpoint);
    }

    @Test
    public void should_prepare_request_initialized() {
        Assertions.assertNotNull(client.getHeaders().get("Accept"));
        Assertions.assertNotNull(client.getHeaders().get("Content-Type"));
        Assertions.assertNull(client.getHeaders().get("Coasdntent-Type"));
    }

    @Test
    public void should_add_header_set_and_get_data() {
        client.addHeader("key1", "value1")
                .addHeader("key2", "value2")
                .addHeader("key3", "value3");

        Assertions.assertNotNull(client.getHeaders().get("key1"));
        Assertions.assertNotNull(client.getHeaders().get("key2"));
        Assertions.assertNotNull(client.getHeaders().get("key3"));
        Assertions.assertNull(client.getHeaders().get("key4"));
    }

    @Test
    public void should_add_post_set_and_get_data() {
        client.addPost("post1","data1")
                .addPost("post2","data2");

        Assertions.assertNotNull(client.getPosts().get("post1"));
        Assertions.assertNotNull(client.getPosts().get("post2"));
        Assertions.assertNull(client.getPosts().get("post3"));

    }

    @Test
    public void should_add_params_send_and_get_data() {
        client.addParam("key1", "value1")
                .addParam("key2", "value2")
                .addParam("key3", "value3");

        Assertions.assertNotNull(client.getParams().get("key1"));
        Assertions.assertNotNull(client.getParams().get("key2"));
        Assertions.assertNotNull(client.getParams().get("key3"));
        Assertions.assertNull(client.getParams().get("key4"));
    }

    @Test
    public void should_get_url_append_params(){
        client.addParam("key1", "value1")
                .addParam("key2", "value2");

        String url = client.getUrl();
        Assertions.assertNotNull(url);
        Assertions.assertEquals(PlantAPIConstants.BASE_URL+testEndpoint+"?key1=value1&key2=value2", url);
    }

}
