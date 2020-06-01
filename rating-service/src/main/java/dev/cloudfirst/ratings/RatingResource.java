package dev.cloudfirst.ratings;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rating")
public class RatingResource {
    Map<Integer, Rating> ratings = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{productId}")
    public Rating hello(@PathParam("productId") int productId) {
        //get rating and return

        return ratings.get(productId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{productId}/upVote")
    public Rating upVote(@PathParam("productId") int productId) {
        //get current or default
        Rating rating = ratings.getOrDefault(productId, new Rating());

        //increment
        rating.votes += 1;

        //set rating
        ratings.put(productId, rating);

        return rating;
    }

}