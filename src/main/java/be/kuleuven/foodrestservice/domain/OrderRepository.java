package be.kuleuven.foodrestservice.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class OrderRepository {

    private final MealsRepository mealsRepository;
    private final Map<String, Order> orders = new HashMap<>();

    public OrderRepository(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    public OrderConfirmation createOrder(Order order) {

        if (order.getId() == null) {
            order.setId(UUID.randomUUID().toString());
        }

        orders.put(order.getId(), order);

        double totalPrice = 0.0;
        List<String> orderedMeals = new ArrayList<>();

        for (String mealId : order.getMealIds()) {
            Optional<Meal> mealOptional = mealsRepository.findMeal(mealId);
            if (mealOptional.isPresent()) {
                Meal meal = mealOptional.get();
                totalPrice += meal.getPrice();
                orderedMeals.add(meal.getName());
            }
        }

        OrderConfirmation confirmation = new OrderConfirmation(
                order.getId(),
                LocalDateTime.now().plusMinutes(30),
                totalPrice,
                orderedMeals,
                order.getAddress()
        );

        return confirmation;
    }

    public Order findOrder(String id) {
        return orders.get(id);
    }

    public Collection<Order> getAllOrders() {
        return orders.values();
    }

    public boolean deleteOrder(String id) {
        if (orders.containsKey(id)) {
            orders.remove(id);
            return true;
        }
        return false;
    }

    public void addOrder(Order o){
        orders.put(o.getId(), o);
    }
}
