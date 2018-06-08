package pl.mh.reactapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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