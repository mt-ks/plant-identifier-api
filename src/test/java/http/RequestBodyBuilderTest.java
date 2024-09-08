package http;

import com.mtks.http.RequestBodyBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class RequestBodyBuilderTest {

    @Test
    public void build_http_query(){
        HashMap<String, String> params = new HashMap<>();
        params.put("name", "mtks");
        params.put("job", "back-end-developer");
        params.put("age", "24 Age");

        String body = RequestBodyBuilder.createQuery(params);
        Assertions.assertNotNull(body);
        Assertions.assertEquals("name=mtks&job=back-end-developer&age=24+Age",body);
    }

    @Test
    public void build_http_json_query(){
        HashMap<String, String> params = new HashMap<>();
        params.put("name", "mtks");
        params.put("job", "back-end-developer");
        String data = RequestBodyBuilder.toJSON(params);
        Assertions.assertNotNull(data);
        Assertions.assertEquals("{\"name\":\"mtks\",\"job\":\"back-end-developer\"}", data);

    }

}
