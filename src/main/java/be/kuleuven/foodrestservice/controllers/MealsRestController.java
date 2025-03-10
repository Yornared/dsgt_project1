package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.*;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import be.kuleuven.foodrestservice.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;
    private final OrderRepository orderRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository, OrderRepository orderRepository) {
        this.mealsRepository = mealsRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/rest/orders")
    public EntityModel<OrderConfirmation> createOrder(@RequestBody Order order) {
        OrderConfirmation confirmation = orderRepository.createOrder(order);
        return EntityModel.of(confirmation,
                linkTo(methodOn(MealsRestController.class).getOrderById(confirmation.getOrderId())).withRel("order"),
                linkTo(methodOn(MealsRestController.class).getAllOrders()).withRel("all-orders"));
    }

    @GetMapping("/rest/orders/{id}")
    public EntityModel<Order> getOrderById(@PathVariable String id) {
        Order order = orderRepository.findOrder(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        return orderToEntityModel(id, order);
    }

    @PutMapping("/rest/orders/{id}")
    public EntityModel<Order> updateOrder(@PathVariable String id, @RequestBody Order updatedOrder) {
        updatedOrder.setId(id);
        orderRepository.addOrder(updatedOrder);
        return orderToEntityModel(id, updatedOrder);
    }

    @GetMapping("/rest/orders")
    public CollectionModel<EntityModel<Order>> getAllOrders() {
        Collection<Order> orders = orderRepository.getAllOrders();

        List<EntityModel<Order>> orderEntityModels = new ArrayList<>();
        for (Order order : orders) {
            EntityModel<Order> em = orderToEntityModel(order.getId(), order);
            orderEntityModels.add(em);
        }

        return CollectionModel.of(orderEntityModels,
                linkTo(methodOn(MealsRestController.class).getAllOrders()).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("meals"));
    }

    @DeleteMapping("/rest/orders/{id}")
    public EntityModel<Map<String, Boolean>> deleteOrder(@PathVariable String id) {
        boolean deleted = orderRepository.deleteOrder(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted", deleted);

        return EntityModel.of(result,
                linkTo(methodOn(MealsRestController.class).getAllOrders()).withRel("all-orders"));
    }

    private EntityModel<Order> orderToEntityModel(String id, Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(MealsRestController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getAllOrders()).withRel("rest/orders"),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

    @GetMapping("/rest/meals/{id}")
    EntityModel<Meal> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealToEntityModel(id, meal);
    }

    @GetMapping("/rest/meals/cheapest")
    EntityModel<Meal> getCheapestMeal() {
        Meal meal = mealsRepository.getAllMeal().stream().min(Comparator.comparingDouble(Meal::getPrice)).orElseThrow();

        return mealToEntityModel(meal.getId(), meal);
    }

    @GetMapping("/rest/meals/largest")
    EntityModel<Meal> getLargestMeal() {
        Meal meal = mealsRepository.getAllMeal().stream().max(Comparator.comparingInt(Meal::getKcal)).orElseThrow();

        return mealToEntityModel(meal.getId(), meal);
    }

    @DeleteMapping("/rest/meals/{id}")
    void deleteMeal(@PathVariable String id) {
        boolean removed = mealsRepository.deleteMeal(id);
        if (!removed) {
            throw new MealNotFoundException(id);
        }
    }

    @PutMapping("/rest/meals/{id}")
    EntityModel<Meal> updateMeal(@PathVariable String id, @RequestBody Meal updatedMeal) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        meal.setName(updatedMeal.getName());
        meal.setPrice(updatedMeal.getPrice());
        meal.setKcal(updatedMeal.getKcal());

        mealsRepository.addMeal(meal);

        return mealToEntityModel(meal.getId(), meal);
    }



    @PostMapping("/rest/meals")
    EntityModel<Meal> addMeal(@RequestBody Meal newMeal) {
        mealsRepository.addMeal(newMeal);  // You need to implement this in MealsRepository
        return mealToEntityModel(newMeal.getId(), newMeal);
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"));
    }

}
