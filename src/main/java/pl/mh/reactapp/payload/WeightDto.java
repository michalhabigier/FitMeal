package pl.mh.reactapp.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeightDto {

    private LocalDate date = LocalDate.now();

    private double weight;

}
