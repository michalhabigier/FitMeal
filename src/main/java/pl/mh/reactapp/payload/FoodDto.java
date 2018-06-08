package pl.mh.reactapp.payload;

import pl.mh.reactapp.util.Constants;

public class FoodDto {

    private String name;

    private double totalCalories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    private double portion;

    public String getName() {
        return name;
    }

    public double getTotalCalories() {
        return totalCalories * getPortion() * Constants.SCALE;
    }

    public double getProteins() {
        return proteins * getPortion() * Constants.SCALE;
    }

    public double getCarbohydrates() {
        return carbohydrates * getPortion() * Constants.SCALE;
    }

    public double getFat() {
        return fat * getPortion() * Constants.SCALE;
    }

    public double getPortion() {
        return portion;
    }
}
