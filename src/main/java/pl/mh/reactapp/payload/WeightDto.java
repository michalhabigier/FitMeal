package pl.mh.reactapp.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeightDto {

    @JsonIgnore
    private LocalDate date = LocalDate.now();

    private double weight;

}
