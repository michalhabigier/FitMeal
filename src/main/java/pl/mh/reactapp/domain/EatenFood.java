package pl.mh.reactapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@Entity
public class EatenFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Double quantity;

    @ManyToOne
    private Food food;

    @ManyToOne
    private Post post;

    private LocalDate localDate;

    public EatenFood(Double quantity, LocalDate localDate, Food food, Post post) {
        this.quantity = quantity;
        this.localDate = localDate;
        this.food = food;
        this.post = post;
    }
}
