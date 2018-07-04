package pl.mh.reactapp.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EatenFoodDto{
    private long id;
    @JsonIgnore
    private long userId;
    private FoodDto foodDto;
    private double quantity;

    private int totalCalories;
    private double fat;
    private double carbohydrates;
    private double proteins;

    public EatenFoodDto(long id, long userId, FoodDto foodDto, double quantity, int totalCalories, double fat, double carbohydrates, double proteins) {
        this.id = id;
        this.userId = userId;
        this.foodDto = foodDto;
        this.quantity = quantity;
        this.totalCalories = totalCalories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
    }
}
