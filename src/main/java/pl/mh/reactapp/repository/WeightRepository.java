package pl.mh.reactapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.domain.Weight;

import java.util.List;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    List<Weight> findByUser(User user);
}
