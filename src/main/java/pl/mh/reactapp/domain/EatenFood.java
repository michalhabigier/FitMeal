package pl.mh.reactapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class EatenFood extends AbstractAuditingClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private Double quantity;

    public EatenFood(User user, Food food, Double quantity) {
        this.user = user;
        this.food = food;
        this.quantity = quantity;
    }
}
