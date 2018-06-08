package pl.mh.reactapp.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {

    private LocalDate localDate = LocalDate.now();

    private List<FoodDto> eatenFoods = new ArrayList<>();

    private double calories = 0.0;

    private double proteins = 0.0;

    private double carbohydrates = 0.0;

    private double fat = 0.0;
}
