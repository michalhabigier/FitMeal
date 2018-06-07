package pl.mh.reactapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Weight extends AbstractAuditingClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private double weight;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private User user;

    public Weight(LocalDate date, double weight, User user) {
        this.date = date;
        this.weight = weight;
        this.user = user;
    }

    public Weight() {
    }
}
