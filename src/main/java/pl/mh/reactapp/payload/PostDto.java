package pl.mh.reactapp.payload;

import lombok.*;
import pl.mh.reactapp.domain.EatenFood;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private LocalDate localDate;

    private List<EatenFood> eatenFoods;

    private double calories;

    private double proteins;

    private double carbohydrates;

    private double fat;


}
