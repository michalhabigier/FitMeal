package pl.mh.reactapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    Post findPostByDate(LocalDate localDate);
    Post findById(long postId);
}
