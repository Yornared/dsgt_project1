package be.kuleuven.foodrestservice.domain;

import java.util.List;

public class Order {
    private String id;
    private String address;
    private List<String> mealIds;

    public Order() {
    }

    public Order(String address, List<String> mealIds) {
        this.id = java.util.UUID.randomUUID().toString();
        this.address = address;
        this.mealIds = mealIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMealIds() {
        return mealIds;
    }

    public void setMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
    }
}
