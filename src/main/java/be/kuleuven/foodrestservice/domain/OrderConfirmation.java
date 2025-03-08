package be.kuleuven.foodrestservice.domain;

import java.time.LocalDateTime;
import java.util.List;

public class OrderConfirmation {
    private String orderId;
    private LocalDateTime estimatedDeliveryTime;
    private double totalPrice;
    private List<String> orderedMeals;
    private String address;

    public OrderConfirmation() {}

    public OrderConfirmation(String orderId, LocalDateTime estimatedDeliveryTime,
                             double totalPrice, List<String> orderedMeals, String address) {
        this.orderId = orderId;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.totalPrice = totalPrice;
        this.orderedMeals = orderedMeals;
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<String> getOrderedMeals() {
        return orderedMeals;
    }

    public void setOrderedMeals(List<String> orderedMeals) {
        this.orderedMeals = orderedMeals;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}