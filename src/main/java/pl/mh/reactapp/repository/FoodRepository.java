package pl.mh.reactapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mh.reactapp.domain.Food;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>{
    List<Food> findByNameContainingIgnoreCase(@Param("name") String name);
}