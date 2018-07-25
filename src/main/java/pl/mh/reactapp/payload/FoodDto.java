package pl.mh.reactapp.payload;

import lombok.Getter;
import lombok.Setter;
import pl.mh.reactapp.domain.Category;

@Getter
@Setter
public class FoodDto {

    private String name;

    private double totalCalories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    private double portion;

    private Category category;

    public FoodDto(String name, double totalCalories, double proteins, double carbohydrates,
                   double fat, double portion, Category category) {
        this.name = name;
        this.totalCalories = totalCalories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.portion = portion;
        this.category = category;
    }

    public FoodDto() {
    }
}
