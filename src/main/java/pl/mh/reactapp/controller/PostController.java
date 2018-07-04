package pl.mh.reactapp.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.Food;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.payload.ApiResponse;
import pl.mh.reactapp.payload.EatenFoodDto;
import pl.mh.reactapp.payload.PostDto;
import pl.mh.reactapp.repository.EatenFoodRepository;
import pl.mh.reactapp.repository.FoodRepository;
import pl.mh.reactapp.repository.PostRepository;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;
import pl.mh.reactapp.service.PostService;
import pl.mh.reactapp.util.ObjectMapperUtils;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final EatenFoodRepository eatenFoodRepository;

    private final FoodRepository foodRepository;

    private final PostService postService;

    public PostController(PostRepository postRepository, UserRepository userRepository, EatenFoodRepository eatenFoodRepository,
                          FoodRepository foodRepository, PostService postService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.eatenFoodRepository = eatenFoodRepository;
        this.foodRepository = foodRepository;
        this.postService = postService;
    }

    @GetMapping("/profile/{username}/posts")
    public List<PostDto> getPostsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        List<Post> posts = postRepository.findAllByUser(user);
        return ObjectMapperUtils.mapAll(posts, PostDto.class);
    }

    @PostMapping("/profile/me/createPost")
    public ResponseEntity<?> createPost(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PostDto postDto) {
        User user = userRepository.findUserById(currentUser.getId());
        Post post = postService.createPost(postDto, user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postDate}")
                .buildAndExpand(post.getDate()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Post Created Successfully"));
    }

    @GetMapping("/profile/me/{localDate}")
    public PostDto getPost(@PathVariable(value = "localDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate postDate, @CurrentUser UserPrincipal currentUser){
        User user = userRepository.findUserById(currentUser.getId());
        Optional<Post> post = postRepository.findAllByUser(user).stream().filter(p -> p.getDate().equals(postDate)).findFirst();
        return ObjectMapperUtils.map(post, PostDto.class);
    }

}
