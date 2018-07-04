package pl.mh.reactapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mh.reactapp.domain.EatenFood;

public interface EatenFoodRepository extends JpaRepository<EatenFood, Long>{
    EatenFood findById(long id);
}
