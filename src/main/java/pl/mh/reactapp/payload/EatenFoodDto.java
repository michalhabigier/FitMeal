package pl.mh.reactapp.payload;

import pl.mh.reactapp.util.FoodConstants;

public class EatenFoodDto {

    private String name;

    private double totalCalories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    private int quantity;

    public int getCalories(){
        return (int)(quantity*totalCalories*FoodConstants.SCALE);
    }

    public double getFat(){
        return fat*quantity*FoodConstants.SCALE;
    }

    public double getProteins(){
        return proteins*quantity*FoodConstants.SCALE;
    }

    public double getCarbohydrates(){
        return carbohydrates*quantity*FoodConstants.SCALE;
    }
}
