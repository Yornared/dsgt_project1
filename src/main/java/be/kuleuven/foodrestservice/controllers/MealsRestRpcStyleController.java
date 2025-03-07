package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
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
        boolean meal = mealsRepository.getAllMeal().removeIf(meal1 -> {
            return meal1.getId().equals(id);
        });
    }

    @PutMapping("/restrpc/meals/{id}")
    void updateMeal(@PathVariable String id, @RequestBody Meal updatedMeal){
        Optional<Meal> existingMeal = mealsRepository.findMeal(id);

        if (existingMeal.isEmpty()) {
            throw new MealNotFoundException(id);
        }

        Meal meal = existingMeal.get();

        meal.setName(updatedMeal.getName());
        meal.setPrice(updatedMeal.getPrice());
        meal.setKcal(updatedMeal.getKcal());
        meal.setId(updatedMeal.getId());
        meal.setDescription(updatedMeal.getDescription());
        meal.setMealType(updatedMeal.getMealType());
    }

    @PostMapping("/restrpc/meals")
    void addMeal(@RequestBody Meal newMeal){
        mealsRepository.addMeal(newMeal);
    }
}
