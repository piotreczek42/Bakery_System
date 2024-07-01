public class IngredientsCalculator {
    public static String getIngredientsList(String product, int quantity) {
        StringBuilder ingredientsList = new StringBuilder("Ingredients List:\n\n");

        switch (product) {
            case "Bread":
                ingredientsList.append("Bread:\n")
                        .append(" - Flour: ").append(500 * quantity).append("g\n")
                        .append(" - Water: ").append(320 * quantity).append("ml\n")
                        .append(" - Yeast: ").append(30 * quantity).append("g\n")
                        .append(" - Sugar: ").append(10 * quantity).append("g\n")
                        .append(" - Salt: ").append(10 * quantity).append("g\n");
                break;
            case "Cookies":
                ingredientsList.append("Cookies:\n")
                        .append(" - Flour: ").append(300 * quantity).append("g\n")
                        .append(" - Sugar: ").append(100 * quantity).append("g\n")
                        .append(" - Butter: ").append(200 * quantity).append("g\n")
                        .append(" - Egg: ").append(quantity).append("\n");
                break;
            case "Cake":
                ingredientsList.append("Cake:\n")
                        .append(" - Plums: ").append(600 * quantity).append("g\n")
                        .append(" - Flour: ").append(320 * quantity).append("g\n")
                        .append(" - Butter: ").append(200 * quantity).append("g\n")
                        .append(" - Sugar: ").append(5 * quantity).append(" tablespoons\n")
                        .append(" - Vanilla Sugar: ").append(1 * quantity).append(" tablespoon\n")
                        .append(" - Baking Powder: ").append(1.5 * quantity).append(" teaspoons\n")
                        .append(" - Egg: ").append(quantity).append("\n");
                break;
            default:
                ingredientsList.append("Unknown product\n");
                break;
        }

        return ingredientsList.toString();
    }
}
