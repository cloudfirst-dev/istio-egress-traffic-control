package dev.cloudfirst.products;

public class Rating {
    public Integer votes = 0;
    public int productId;

    public Rating() {}

    public Rating(int productId) {
        this.productId = productId;
    }
}