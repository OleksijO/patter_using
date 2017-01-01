package training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksij.onysymchuk@gmail on 30.12.2016.
 */
public class PizzaPriceCalculating {

    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria();
        System.out.println("Making pizza with default price calculator:");
        Pizza newPizza = pizzeria.makePizzaWithMushroomsWithoutProfit();
        System.out.println(newPizza);
        System.out.println("Making pizza with price calculator with profit decorator:");
        newPizza = pizzeria.makePizzaWithMushroomsWithProfit();
        System.out.println(newPizza);
        System.out.println("Making pizza with default price calculator:");
        newPizza = pizzeria.makePizzaWithOlivesAndDoubleCheeseWithoutProfit();
        System.out.println(newPizza);
        System.out.println("Making pizza with price calculator with profit decorator:");
        newPizza = pizzeria.makePizzaWithOlivesAndDoubleCheeseWithProfit();
        System.out.println(newPizza);
    }

    static class Pizzeria {
        Pizza makePizzaWithMushroomsWithoutProfit() {
            return new PizzaImpl.Builder()
                    .setName("Pizza with mushrooms")
                    .addIngredient(Ingredients.FLOWER.getCopy())
                    .addIngredient(Ingredients.WATER.getCopy())
                    .addIngredient(Ingredients.SOUSE.getCopy())
                    .addIngredient(Ingredients.MUSHROOMS.getCopy())
                    .addIngredient(Ingredients.CHEESE.getCopy())
                    //.setPizzaPriceCalculatorWithProfit()
                    .make();
        }

        Pizza makePizzaWithMushroomsWithProfit() {
            return new PizzaImpl.Builder()
                    .setName("Pizza with mushrooms")
                    .addIngredient(Ingredients.FLOWER.getCopy())
                    .addIngredient(Ingredients.WATER.getCopy())
                    .addIngredient(Ingredients.SOUSE.getCopy())
                    .addIngredient(Ingredients.MUSHROOMS.getCopy())
                    .addIngredient(Ingredients.CHEESE.getCopy())
                    .setPizzaPriceCalculatorWithProfit()
                    .make();
        }

        Pizza makePizzaWithOlivesAndDoubleCheeseWithoutProfit() {
            return new PizzaImpl.Builder()
                    .setName("Pizza with mushrooms")
                    .addIngredient(Ingredients.FLOWER.getCopy())
                    .addIngredient(Ingredients.WATER.getCopy())
                    .addIngredient(Ingredients.SOUSE.getCopy())
                    .addIngredient(Ingredients.OLIVES.getCopy())
                    .addIngredient(Ingredients.CHEESE.getCopy())
                    .addIngredient(Ingredients.CHEESE.getCopy())
                    //.setPizzaPriceCalculatorWithProfit()
                    .make();
        }

        Pizza makePizzaWithOlivesAndDoubleCheeseWithProfit() {
            return new PizzaImpl.Builder()
                    .setName("Pizza with mushrooms")
                    .addIngredient(Ingredients.FLOWER.getCopy())
                    .addIngredient(Ingredients.WATER.getCopy())
                    .addIngredient(Ingredients.SOUSE.getCopy())
                    .addIngredient(Ingredients.OLIVES.getCopy())
                    .addIngredient(Ingredients.CHEESE.getCopy())
                    .addIngredient(Ingredients.CHEESE.getCopy())
                    .setPizzaPriceCalculatorWithProfit()
                    .make();
        }
    }


    static class PizzaImpl implements Pizza {
        String name;
        List<Ingredient> ingredients = new ArrayList<>();
        PriceCalculator priceCalculator;

        @Override
        public int getPrice() {
            return priceCalculator.calculatePrice();
        }

        public void addIngredient(Ingredient ingredient) {
            ingredients.add(ingredient);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        @Override
        public void setPizzaPriceCalculator(PriceCalculator priceCalculator) {
            this.priceCalculator = priceCalculator;
        }

        @Override
        public String toString() {
            return "PizzaImpl{" +
                    "name='" + name + '\'' +
                    ", price=" + priceCalculator.calculatePrice() +
                    '}';
        }

        static class Builder {
            PizzaImpl pizza = new PizzaImpl();

            {
                pizza.priceCalculator = new DefaultPizzaPriceCalculator(pizza);
            }

            public Builder setName(String name) {
                pizza.name = name;
                return this;
            }

            public Builder setPizzaPriceCalculatorWithProfit() {
                pizza.priceCalculator = new PizzaPriceCalculatorProfitDecorator(new DefaultPizzaPriceCalculator(pizza));
                return this;
            }

            public Builder addIngredient(Ingredient ingredient) {
                if (!pizza.ingredients.contains(ingredient)) {
                    pizza.ingredients.add(ingredient);
                }
                pizza.ingredients.get(pizza.ingredients.indexOf(ingredient)).addQuantity(1);

                return this;
            }

            public Pizza make() {
                return pizza;
            }
        }
    }

    interface PriceCalculator {
        int calculatePrice();
    }

    interface Pizza {
        int getPrice();

        void addIngredient(Ingredient ingredient);

        List<Ingredient> getIngredients();

        void setPizzaPriceCalculator(PriceCalculator priceCalculator);
    }

    static class Ingredient {
        private String name;
        private int price;
        private int quantity;

        public Ingredient(String name, int price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void addQuantity(int quantityToAdd) {
            this.quantity += quantityToAdd;
        }

        public int getTotalPrice() {
            return price * quantity;
        }

        @Override
        public String toString() {
            return "Ingredient{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    '}';
        }
    }

    static class PizzaPriceCalculatorProfitDecorator implements PriceCalculator {
        Pizza pizza;
        int profitPercentage = 20;
        PriceCalculator originalPriceCalculator;

        public PizzaPriceCalculatorProfitDecorator(PriceCalculator originalPriceCalculator) {
            this.originalPriceCalculator = originalPriceCalculator;
        }

        @Override
        public int calculatePrice() {
            return originalPriceCalculator.calculatePrice() * (100 + profitPercentage) / 100;
        }

    }

    static class DefaultPizzaPriceCalculator implements PriceCalculator {
        Pizza pizza;

        public DefaultPizzaPriceCalculator(Pizza pizza) {
            this.pizza = pizza;
        }

        @Override
        public int calculatePrice() {
            return pizza.getIngredients().stream().mapToInt(Ingredient::getTotalPrice).sum();
        }
    }

    enum Ingredients {
        FLOWER(new Ingredient("Flower", 1000, 0)),
        WATER(new Ingredient("Water", 100, 0)),
        SALAMI(new Ingredient("Salami", 1200, 0)),
        OLIVES(new Ingredient("Olives", 800, 0)),
        SOUSE(new Ingredient("Souse", 400, 0)),
        MUSHROOMS(new Ingredient("Mushrooms", 700, 0)),
        CHEESE(new Ingredient("Cheese", 1300, 0));

        private Ingredient ingredient;

        Ingredients(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        public Ingredient getCopy() {
            return new Ingredient(ingredient.getName(), ingredient.getPrice(), ingredient.getQuantity());
        }
    }


}
