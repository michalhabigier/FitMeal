package pl.mh.reactapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Food extends AbstractAuditingClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double totalCalories;

    private double proteins;

    private double carbohydrates;

    private double fat;

    private Category category;

    public Food() {
    }

    public Food(String name, double totalCalories, double proteins, double carbohydrates, double fat, Category category) {
        this.name = name;
        this.totalCalories = totalCalories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.category = category;
    }
}