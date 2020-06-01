package dev.cloudfirst.products;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/product")
public class ProductResource {
    @Inject
    @RestClient
    RatingClient ratingClient;

    static Map<Integer, Product> products = new HashMap<>();

    static {
        Product product = new Product();

        product.productName = "Test Product";
        product.id = 1;

        products.put(product.id, product);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{productId}")
    public Product hello(@PathParam("productId") Integer productId) {
        //get ratings
        Optional<Rating> rating = Optional.ofNullable(ratingClient.getById(productId));
        Product product = products.getOrDefault(productId, new Product());
        product.votes = rating.orElseGet(() -> new Rating()).votes;

        return product;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{productId}/vote")
    public Product vote(@PathParam("productId") Integer productId) {
        //get ratings
        Optional<Rating> rating = Optional.ofNullable(ratingClient.voteByProductId(productId));
        Product product = products.getOrDefault(productId, new Product());
        product.votes = rating.orElseGet(() -> new Rating()).votes;

        return product;
    }
}