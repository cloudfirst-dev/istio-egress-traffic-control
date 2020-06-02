package dev.cloudfirst.products;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@RegisterClientHeaders
public interface RatingClient {
    @GET
    @Path("/rating/{productId}")
    @Produces("application/json")
    Rating getById(@PathParam("productId") Integer productId);


    @POST
    @Path("/rating/{productId}/upVote")
    @Produces("application/json")
    Rating voteByProductId(@PathParam("productId") Integer productId);
}