package pl.mh.reactapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @OneToMany
    private List<Food> foodList;

    private double calories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    @ManyToOne
    private User user;

    public void addFood(Food food) {
        foodList.add(food);
    }
}
