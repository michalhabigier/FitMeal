package pl.mh.reactapp.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pl.mh.reactapp.domain.Activity;
import pl.mh.reactapp.domain.Sex;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CalculatorDto {
    @NotNull
    private double weight;
    @NotNull
    private int height;
    @NotNull
    private int age;
    @NotNull
    private Sex sex;
    @NotNull
    private double activity;

    private double bmi;

    private double bmr;
}
