package org.geobricks.rest;

import org.junit.Test;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class TestHelloWorldRest extends JerseyTest {

    private WebResource ws;

    public TestHelloWorldRest() {
        super(new WebAppDescriptor.Builder("org.geobricks.rest").build());
    }

    @Test
    public void testHello(){
        ws = resource().path("hello/Guido");
        ClientResponse response = ws.get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String out = response.getEntity(String.class);
        assertEquals("Hello Guido!", out);
    }

    @Test
    public void testHelloJSON(){
        ws = resource().path("hello/json/Guido");
        ClientResponse response = ws.get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String out = response.getEntity(String.class);
        assertEquals("{\"hello\":\"Guido\"}", out);
    }

}