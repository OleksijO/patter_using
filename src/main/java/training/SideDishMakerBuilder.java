package training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oleksij.onysymchuk@gmail on 14.12.2016.
 */
public class SideDishMakerBuilder {


    public static void main(String[] args) {
        SideDishMakerBuilder test = new SideDishMakerBuilder();
        SideDishMaker dishMaker = test.new SideDishMaker();
        List<Ingredient> customerIngredients = new ArrayList<Ingredient>() {{
            add(test.new IngredientImpl("Oil", 1));
            add(test.new IngredientImpl("Salt", 1));
            add(test.new IngredientImpl("Eggs", 1));
            add(test.new IngredientImpl("Salad", 1));
        }};

        System.out.println(dishMaker.makeSideDish(customerIngredients));
        try {
            System.out.println(dishMaker.makeSideDish(customerIngredients));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        customerIngredients.add(0, test.new IngredientImpl("Fried Potato", 1));
        try {
            System.out.println(dishMaker.makeSideDish(customerIngredients));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    interface Ingredient {
        int getQuantity();

        void add(int quantityToAdd);

        void subtract(int quantityToSubtract);

        String toString();

        String getName();
    }

    class SideDishMaker {
        private List<Ingredient> ingredients = new ArrayList<>();

        public SideDishMaker() {
            ingredients.add(new IngredientImpl("Oil", 5));
            ingredients.add(new IngredientImpl("Salad", 5));
            ingredients.add(new IngredientImpl("Salt", 1));
            ingredients.add(new IngredientImpl("Eggs", 5));
            ingredients.add(new IngredientImpl("Tomato", 5));
            ingredients.add(new IngredientImpl("Cucumber", 5));
        }

        List<Ingredient> makeSideDish(List<Ingredient> customerIngredients) {
            checkAvailability(customerIngredients);
            customerIngredients.forEach(ingredient -> {
                Ingredient availableIngredient = ingredients.get(ingredients.indexOf(ingredient));
                availableIngredient.subtract(ingredient.getQuantity());
            });
            Collections.shuffle(customerIngredients);
            return customerIngredients;
        }

        private void checkAvailability(List<Ingredient> customerIngredients) {
            customerIngredients.forEach(ingredient -> {
                if (ingredients.contains(ingredient)) {
                    Ingredient availableIngredient = ingredients.get(ingredients.indexOf(ingredient));
                    if (availableIngredient.getQuantity() < ingredient.getQuantity()) {
                        throw new RuntimeException("Not enough " + ingredient.getName()
                                + ". Needed=" + ingredient.getQuantity()
                                + ", available=" + availableIngredient.getQuantity());
                    }
                } else {
                    throw new RuntimeException("Unknown ingredient: " + ingredient.getName());
                }
            });
        }


    }

    class IngredientImpl implements Ingredient {
        private String name;
        private int quantity;

        public IngredientImpl(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public void add(int quantityToAdd) {
            quantity += quantityToAdd;
        }

        @Override
        public void subtract(int quantityToSubtract) {
            if (quantityToSubtract > quantity) {
                throw new IllegalArgumentException();
            }
            quantity -= quantityToSubtract;
        }


        @Override
        public String getName() {
            return name;
        }

        public String toString() {
            return name + "(" + quantity + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IngredientImpl that = (IngredientImpl) o;

            return name != null ? name.equals(that.name) : that.name == null;

        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }


}
