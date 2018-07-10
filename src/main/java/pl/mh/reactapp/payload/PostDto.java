package pl.mh.reactapp.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.mh.reactapp.domain.EatenFood;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {

    private LocalDate localDate = LocalDate.now();

    private List<EatenFood> eatenFoods = new ArrayList<>();

    private double calories;

    private double proteins;

    private double carbohydrates;

    private double fat;
}
