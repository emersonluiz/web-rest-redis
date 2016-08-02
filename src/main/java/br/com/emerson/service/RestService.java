package br.com.emerson.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.emerson.model.User;

@Path("/")
public interface RestService {

    @POST
    @Consumes("application/json")
    Response createSession(@Context UriInfo uriInfo, User user);

    @GET
    @Path("/{token}")
    @Produces("application/json")
    Response getSession(@PathParam("token") String uuid);

    @DELETE
    @Path("/{token}")
    @Produces("application/json")
    Response deleteSession(@PathParam("token") String uuid);
}
