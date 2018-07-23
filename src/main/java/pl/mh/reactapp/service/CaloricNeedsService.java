package pl.mh.reactapp.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.Activity;
import pl.mh.reactapp.domain.Sex;
import pl.mh.reactapp.payload.CalculatorDto;
import pl.mh.reactapp.util.AmountRounder;

import javax.validation.Valid;

@Getter
@Setter
@Service
public class CaloricNeedsService {

    public double calculateBmr(Sex sex, double weight, int height, int age, double activityLevel) {
        double activityRatio = Activity.fromValue(activityLevel).getValue();
        if (sex == Sex.FEMALE)
            return activityRatio*((9.99 * weight) + (6.25 * height) - 4.92 * age - 161);
        else
            return activityRatio*((9.99 * weight) + (6.25 * height) - 4.92 * age + 5);
    }

    public double calculateBmi(double weight, int height) {
        return Math.pow(10, 4)*weight/(Math.pow(height, 2)); //formula contains height in meters,
        // we convert it into centimeters by multiplying by 10000
    }

    public CalculatorDto calculateBmi(@Valid CalculatorDto calculatorDto) {
        double bmi = calculateBmi(calculatorDto.getWeight(), calculatorDto.getHeight());
        calculatorDto.setBmi(AmountRounder.round(bmi));
        return calculatorDto;
    }

    public CalculatorDto calculateBmr(@Valid CalculatorDto calculatorDto) {
        double bmr = calculateBmr(calculatorDto.getSex(), calculatorDto.getWeight(), calculatorDto.getHeight(),
                calculatorDto.getAge(), calculatorDto.getActivity());
        calculatorDto.setBmr(AmountRounder.round(bmr));
        return calculatorDto;
    }
}
