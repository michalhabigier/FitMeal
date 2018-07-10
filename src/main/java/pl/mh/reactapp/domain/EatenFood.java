package pl.mh.reactapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class EatenFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Double quantity;

    private String name;

    private double totalCalories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    @JsonIgnore
    @ManyToOne
    private Food food;

    @JsonIgnore
    @ManyToOne
    private Post post;

    public EatenFood(Double quantity, Food food, Post post) {
        this.quantity = quantity;
        this.food = food;
        this.post = post;
    }

    public EatenFood() {
    }
}
