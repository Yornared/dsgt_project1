package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.*;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;
    private final OrderRepository orderRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository, OrderRepository orderRepository) {
        this.mealsRepository = mealsRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/restrpc/orders")
    public OrderConfirmation createOrder(@RequestBody Order order) {
        return orderRepository.createOrder(order);
    }

    @GetMapping("/restrpc/orders/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderRepository.findOrder(id);
    }

    @PutMapping("/restrpc/orders/{id}")
    public void updateOrder(@PathVariable String id, @RequestBody Order updatedOrder){
        updatedOrder.setId(id);
        orderRepository.addOrder(updatedOrder);
    }

    @GetMapping("/restrpc/orders")
    public Collection<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @DeleteMapping("/restrpc/orders/{id}")
    public void deleteOrder(@PathVariable String id) {
        boolean deleted = orderRepository.deleteOrder(id);
    }

    @GetMapping("/restrpc/meals/{id}")
    Meal getMealById(@PathVariable String id) {
        Optional<Meal> meal = mealsRepository.findMeal(id);

        return meal.orElseThrow(() -> new MealNotFoundException(id));
    }

    @GetMapping("/restrpc/meals/cheapest")
    Meal getCheapestMeal(){
        Optional<Meal> cheapestMeal = mealsRepository.getAllMeal().stream().min(Comparator.comparingDouble(Meal::getPrice));
        return cheapestMeal.orElseThrow();
    }

    @GetMapping("/restrpc/meals/largest")
    Meal getLargestMeal(){
        Optional<Meal> cheapestMeal = mealsRepository.getAllMeal().stream().max(Comparator.comparingInt(Meal::getKcal));
        return cheapestMeal.orElseThrow();
    }

    @GetMapping("/restrpc/meals")
    Collection<Meal> getMeals() {
        return mealsRepository.getAllMeal();
    }

    @DeleteMapping("/restrpc/meals/{id}")
    void deleteMeal(@PathVariable String id){
        mealsRepository.deleteMeal(id);
    }

    @PutMapping("/restrpc/meals/{id}")
    void updateMeal(@PathVariable String id, @RequestBody Meal updatedMeal){
        updatedMeal.setId(id);
        mealsRepository.addMeal(updatedMeal);
    }

    @PostMapping("/restrpc/meals")
    void addMeal(@RequestBody Meal newMeal){
        mealsRepository.addMeal(newMeal);
    }
}
