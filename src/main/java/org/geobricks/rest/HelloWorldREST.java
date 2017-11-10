package org.geobricks.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.geobricks.core.HelloWorldCore;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class HelloWorldREST {

    @GET
    @Path("{name}")
    public Response sayHello(@PathParam("name") String name) {

        /* Load core library. */
        HelloWorldCore c = new HelloWorldCore();

        /* Stream result */
        return Response.status(200).entity(c.sayHello(name)).build();

    }

    @GET
    @Path("json/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHelloJSON(@PathParam("name") String name) {

        /* Load core library. */
        HelloWorldCore c = new HelloWorldCore();

        /* Stream result */
        return Response.status(200).entity(c.sayHelloJSON(name)).build();

    }


}