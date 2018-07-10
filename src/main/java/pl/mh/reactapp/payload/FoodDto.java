package pl.mh.reactapp.payload;

import lombok.Getter;
import lombok.Setter;
import pl.mh.reactapp.domain.Food;

@Getter
@Setter
public class FoodDto {

    private String name;

    private double totalCalories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    private double portion;
}
