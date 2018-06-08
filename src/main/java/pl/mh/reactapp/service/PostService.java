package pl.mh.reactapp.service;

import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.payload.PostDto;
import pl.mh.reactapp.repository.PostRepository;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.util.ObjectMapperUtils;

import javax.validation.Valid;

@Service
public class PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Post createPost(@Valid PostDto postDto, User user) {
        Post post = new Post();
        post.setProteins(postDto.getProteins());
        post.setCarbohydrates(postDto.getCarbohydrates());
        post.setFat(postDto.getFat());
        post.setUser(user);
        post.setDate(postDto.getLocalDate());
        post.setCalories(postDto.getCalories());
        return postRepository.save(post);
    }
}
