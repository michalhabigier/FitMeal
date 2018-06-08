package pl.mh.reactapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.payload.ApiResponse;
import pl.mh.reactapp.payload.PostDto;
import pl.mh.reactapp.repository.PostRepository;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;
import pl.mh.reactapp.service.PostService;
import pl.mh.reactapp.util.ObjectMapperUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    //TODO Post Entity
    @GetMapping("/profile/{username}/posts")
    public List<PostDto> getPostsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        List<Post> posts = postRepository.findByUser(user);
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
}
