package dev.cloudfirst.ratings;

public class Rating {
    public int votes = 0;
    public int productId;

    public Rating() {}

    public Rating(int productId) {
        this.productId = productId;
    }
}