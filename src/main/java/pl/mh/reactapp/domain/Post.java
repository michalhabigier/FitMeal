package pl.mh.reactapp.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @OneToMany
    private List<EatenFood> foodList;

    private double calories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    @ManyToOne
    private User user;

    public Post(LocalDate date, List<EatenFood> foodList, double calories, double proteins, double carbohydrates, double fat, User user) {
        this.date = date;
        this.foodList = foodList;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.user = user;
    }

    public Post() {
    }
}
