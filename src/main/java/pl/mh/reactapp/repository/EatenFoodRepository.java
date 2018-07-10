package pl.mh.reactapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mh.reactapp.domain.EatenFood;
import pl.mh.reactapp.domain.Post;

import java.util.List;

public interface EatenFoodRepository extends JpaRepository<EatenFood, Long> {
    EatenFood findById(long id);

    EatenFood findByPostAndFoodId(Post post, long foodId);

    List<EatenFood> findByPost(Post post);
}
