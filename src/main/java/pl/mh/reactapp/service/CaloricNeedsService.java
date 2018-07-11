package pl.mh.reactapp.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.Sex;
import pl.mh.reactapp.payload.CalculatorDto;
import pl.mh.reactapp.util.AmountRounder;
import pl.mh.reactapp.util.Constants;

import javax.validation.Valid;

@Getter
@Setter
@Service
public class CaloricNeedsService {

    private final double PERCENTS_SCALE = 0.01;

    private double weight;
    private double height;
    private double age;
    private Sex sex;
    private int workoutTimeInMinutes;
    private int cardioTimeInMinutes;
    private int cardioIntensity;
    private int workoutIntensity;
    private double caloriesBurntAfterWorkoutRatio;
    private double caloriesBurntAfterCardio;
    private int NEAT;

    private double getBMR(){
        if(sex==Sex.FEMALE)
            return (9.99*weight)+(6.25*height)-4.92*age - 161;
        else
            return (9.99*weight)+(6.25*height)-4.92*age + 5;
    }

    private double getTEA(){
        return (cardioIntensity*PERCENTS_SCALE*cardioTimeInMinutes+workoutIntensity*workoutTimeInMinutes*PERCENTS_SCALE
                +getEPOC())/ Constants.DAYS_IN_WEEK;
    }

    private double getEPOC(){
        return caloriesBurntAfterWorkoutRatio*PERCENTS_SCALE*getBMR()+caloriesBurntAfterCardio;
    }

    private double calculate(){
        double value = getBMR()+getTEA()+NEAT;
        return value + value/10;
    }

    public CalculatorDto calculateTotal(@Valid CalculatorDto calculatorDto){
        weight = calculatorDto.getWeight();
        height = calculatorDto.getHeight();
        age = calculatorDto.getAge();
        sex = calculatorDto.getSex();
        cardioTimeInMinutes = calculatorDto.getCardioTimeInMinutes();
        workoutTimeInMinutes = calculatorDto.getWorkoutTimeInMinutes();
        cardioIntensity = calculatorDto.getCardioIntensity();
        workoutIntensity = calculatorDto.getWorkoutIntensity();
        caloriesBurntAfterWorkoutRatio = calculatorDto.getCaloriesBurntAfterWorkoutRatio();
        caloriesBurntAfterCardio = calculatorDto.getCaloriesBurntAfterCardio();
        NEAT = calculatorDto.getNEAT();
        calculatorDto.setTotalCalories(AmountRounder.round(this.calculate()));
        return calculatorDto;
    }

}
