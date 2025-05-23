package be.kuleuven.foodrestservice.domain;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class MealsRepository {
    private static final Map<String, Meal> meals = new HashMap<>();

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setId("5268203c-de76-4921-a3e3-439db69c462a");
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealType(MealType.MEAT);
        a.setKcal(1100);
        a.setPrice((10.00));

        meals.put(a.getId(), a);

        Meal b = new Meal();
        b.setId("4237681a-441f-47fc-a747-8e0169bacea1");
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealType(MealType.VEGAN);
        b.setKcal(1100);
        b.setPrice((7.00));

        meals.put(b.getId(), b);

        Meal c = new Meal();
        c.setId("cfd1601f-29a0-485d-8d21-7607ec0340c8");
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealType(MealType.FISH);
        c.setKcal(950);
        c.setPrice(5.00);

        meals.put(c.getId(), c);

        generateFakeMeals(9997);
    }

    public void generateFakeMeals(int numberOfMeals) {
        String[] meatNames = {"Beef Burger", "Chicken Pasta", "BBQ Ribs", "Lamb Chops", "Meatballs",
                "Beef Stroganoff", "Chicken Curry", "Pork Chops", "T-Bone Steak", "Chicken Schnitzel"};

        String[] meatDescriptions = {"Juicy beef patty with cheese", "Grilled chicken with creamy pasta",
                "Slow-cooked ribs with BBQ sauce", "Grilled lamb with mint sauce",
                "Italian meatballs in tomato sauce", "Tender beef in creamy sauce",
                "Spicy chicken curry with rice", "Grilled pork chops with applesauce",
                "Grilled T-bone with vegetables", "Breaded chicken with lemon"};

        String[] fishNames = {"Grilled Salmon", "Tuna Steak", "Fish Tacos", "Shrimp Scampi", "Seafood Paella",
                "Cod Fillet", "Trout Almondine", "Calamari", "Lobster Thermidor", "Crab Cakes"};

        String[] fishDescriptions = {"Fresh salmon with dill sauce", "Seared tuna with sesame crust",
                "Crispy fish tacos with slaw", "Garlic butter shrimp with pasta",
                "Spanish seafood rice dish", "Baked cod with lemon butter",
                "Pan-fried trout with almonds", "Crispy fried squid with aioli",
                "Lobster with creamy cheese sauce", "Pan-fried crab cakes with remoulade"};

        String[] veganNames = {"Beyond Burger", "Falafel Wrap", "Tofu Stir-Fry", "Vegan Lasagna", "Vegetable Curry",
                "Mushroom Risotto", "Eggplant Parmesan", "Vegan Pad Thai", "Quinoa Bowl", "Veggie Paella"};

        String[] veganDescriptions = {"Plant-based burger with vegan cheese", "Chickpea patties in pita bread",
                "Tofu with vegetables in soy sauce", "Pasta layers with vegan cheese and vegetables",
                "Mixed vegetables in spicy sauce", "Creamy rice with mushrooms",
                "Breaded eggplant with tomato sauce", "Rice noodles with tofu and vegetables",
                "Quinoa with roasted vegetables", "Saffron rice with vegetables"};

        String[] veggieNames = {"Vegetarian Lasagna", "Caprese Salad", "Veggie Stir Fry", "Cheese Pizza",
                "Vegetable Quiche", "Spinach Omelette", "Greek Salad", "Vegetable Soup",
                "Cheese Fondue", "Egg Fried Rice"};

        String[] veggieDescriptions = {"Layers of pasta with vegetables", "Fresh tomatoes with mozzarella",
                "Mixed vegetables in sauce", "Classic cheese and tomato pizza",
                "Egg custard with vegetables", "Fluffy eggs with spinach",
                "Mediterranean salad with feta", "Hearty vegetable soup",
                "Melted cheese with bread", "Rice with vegetables and eggs"};

        Random random = new Random();

        for (int i = 0; i < numberOfMeals; i++) {
            Meal meal = new Meal();
            meal.setId(UUID.randomUUID().toString());

            MealType[] mealTypes = MealType.values();
            MealType mealType = mealTypes[random.nextInt(mealTypes.length)];
            meal.setMealType(mealType);

            switch (mealType) {
                case MEAT:
                    meal.setName(meatNames[random.nextInt(meatNames.length)]);
                    meal.setDescription(meatDescriptions[random.nextInt(meatDescriptions.length)]);
                    meal.setKcal(800 + random.nextInt(1200));
                    meal.setPrice(8.00 + (double) Math.round(random.nextDouble() * 24 * 100) / 100);
                    break;
                case FISH:
                    meal.setName(fishNames[random.nextInt(fishNames.length)]);
                    meal.setDescription(fishDescriptions[random.nextInt(fishDescriptions.length)]);
                    meal.setKcal(600 + random.nextInt(1100));
                    meal.setPrice(10.00 + (double) Math.round(random.nextDouble() * 30 * 100) / 100);
                    break;
                case VEGAN:
                    meal.setName(veganNames[random.nextInt(veganNames.length)]);
                    meal.setDescription(veganDescriptions[random.nextInt(veganDescriptions.length)]);
                    meal.setKcal(500 + random.nextInt(1000));
                    meal.setPrice(7.00 + (double) Math.round(random.nextDouble() * 16 * 100) / 100);
                    break;
                case VEGGIE:
                    meal.setName(veggieNames[random.nextInt(veggieNames.length)]);
                    meal.setDescription(veggieDescriptions[random.nextInt(veggieDescriptions.length)]);
                    meal.setKcal(550 + random.nextInt(1050));
                    meal.setPrice(6.00 + (double) Math.round(random.nextDouble() * 20 * 100) / 100);
                    break;
            }
            meals.put(meal.getId(), meal);
        }
    }

    public Optional<Meal> findMeal(String id) {
        Assert.notNull(id, "The meal id must not be null");
        Meal meal = meals.get(id);
        return Optional.ofNullable(meal);
    }

    public Collection<Meal> getAllMeal() {
        return meals.values();
    }

    public boolean deleteMeal(String id) {
        return meals.remove(id) != null;
    }

    public void addMeal(Meal m){
        meals.put(m.getId(), m);
    }
}
