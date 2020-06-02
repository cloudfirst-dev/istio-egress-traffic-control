package dev.cloudfirst.ratings;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/rating")
public class RatingResource {
    Map<Integer, Rating> ratings = new HashMap<>();

    @ConfigProperty(name = "cloudfirst.service.delay")
    long pause;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{productId}")
    public Rating hello(@PathParam("productId") int productId, @HeaderParam("plan-type") String planType) throws InterruptedException {
        //get rating and return

        if(!"gold".equals(planType)) {
            Thread.sleep(pause * 1000l);
        }        

        return ratings.getOrDefault(productId, new Rating(productId));
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