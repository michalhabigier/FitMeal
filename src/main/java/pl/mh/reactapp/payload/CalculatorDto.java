package pl.mh.reactapp.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pl.mh.reactapp.domain.Sex;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CalculatorDto {
    @NotNull
    private double weight;
    @NotNull
    private double height;
    @NotNull
    private double age;
    @NotNull
    private Sex sex;
    @NotNull
    private int workoutTimeInMinutes;
    @NotNull
    private int cardioTimeInMinutes;
    @Min(5)
    @Max(10)
    private int cardioIntensity;
    @Min(7)
    @Max(9)
    private int workoutIntensity;
    @Min(4)
    @Max(7)
    private double caloriesBurntAfterWorkoutRatio;
    @NotNull
    private double caloriesBurntAfterCardio;
    @Min(200)
    @Max(900)
    private int NEAT;
    private double totalCalories;
}
