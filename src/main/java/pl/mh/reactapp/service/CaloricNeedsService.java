package pl.mh.reactapp.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.Activity;
import pl.mh.reactapp.domain.Sex;
import pl.mh.reactapp.payload.CalculatorDto;
import pl.mh.reactapp.util.AmountRounder;
import pl.mh.reactapp.util.Constants;

import javax.validation.Valid;
import java.math.BigDecimal;

@Getter
@Setter
@Service
public class CaloricNeedsService {

    public double calculateBmr(Sex sex, double weight, int height, int age, double activityLevel) {
        double activityRatio = Activity.fromValue(activityLevel).getValue();
        if (sex == Sex.FEMALE)
            return activityRatio*(9.99 * weight) + (6.25 * height) - 4.92 * age - 161;
        else
            return activityRatio*(9.99 * weight) + (6.25 * height) - 4.92 * age + 5;
    }

    public double calculateBmi(double weight, int height) {
        return weight/height*height;
    }

    public CalculatorDto calculateBmi(@Valid CalculatorDto calculatorDto) {
        calculatorDto.setBmi(calculateBmi(calculatorDto.getWeight(), calculatorDto.getHeight()));
        return calculatorDto;
    }

    public CalculatorDto calculateBmr(@Valid CalculatorDto calculatorDto) {
        calculatorDto.setBmr(calculateBmr(calculatorDto.getSex(), calculatorDto.getWeight(), calculatorDto.getHeight(),
                calculatorDto.getAge(), calculatorDto.getActivity()));
        return calculatorDto;
    }

}
