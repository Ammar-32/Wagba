package com.example.wagba.Model;

public class FoodModel {
    private String foodName;
    private String foodImage;
    private String foodDescription;
    private String foodPrice;
    private String restaurantID;
    private String foodAvailability;

    public FoodModel() {
    }

    public FoodModel(String foodName, String foodImage, String foodDescription, String foodPrice, String restaurantID, String foodAvailability) {
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodDescription = foodDescription;
        this.foodPrice = foodPrice;
        this.restaurantID = restaurantID;
        this.foodAvailability = foodAvailability;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getFoodAvailability() {
        return foodAvailability;
    }

    public void setFoodAvailability(String foodAvailability) {
        this.foodAvailability = foodAvailability;
    }
}
