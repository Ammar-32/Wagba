package com.example.wagba.Model;

public class RestaurantModel {
    private String restaurantName;
    private String restaurantImage;
    private String restaurantCategory;
    private String restaurantRating;

    public RestaurantModel() {
    }

    public RestaurantModel(String restaurantName, String restaurantImage, String restaurantCategory, String restaurantRating) {
        this.restaurantName = restaurantName;
        this.restaurantImage = restaurantImage;
        this.restaurantCategory = restaurantCategory;
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantCategory() {
        return restaurantCategory;
    }

    public void setRestaurantCategory(String restaurantCategory) {
        this.restaurantCategory = restaurantCategory;
    }

    public String getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(String restaurantRating) {
        this.restaurantRating = restaurantRating;
    }
}