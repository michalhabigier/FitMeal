package pl.mh.reactapp.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.payload.ApiResponse;
import pl.mh.reactapp.payload.FoodDto;
import pl.mh.reactapp.payload.PostDto;
import pl.mh.reactapp.repository.PostRepository;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;
import pl.mh.reactapp.service.PostService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("api/user")
public class PostController {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PostService postService;

    public PostController(PostRepository postRepository, UserRepository userRepository, PostService postService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postService = postService;
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
    public PostDto getPost(@PathVariable(value = "localDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate postDate,
                           @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findUserById(currentUser.getId());
        Post post = postRepository.findByUser(user)
                .stream()
                .filter(p -> p.getDate().equals(postDate))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Date", "localDate", postDate));
        return postService.calculate(post);
    }

    @PostMapping("/profile/me/{localDate}/{foodId}")
    public ResponseEntity<?> addEatenFood(@RequestBody @Valid FoodDto eatenFoodDto, @PathVariable long foodId, @PathVariable(value = "localDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate postDate, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findUserById(currentUser.getId());
        Post post = postRepository.findByUser(user)
                .stream()
                .filter(p -> p.getDate().equals(postDate))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Date", "localDate", postDate));

        postService.addEatenFood(post.getId(), foodId, eatenFoodDto);
        postService.calculate(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postDate}")
                .buildAndExpand(post.getDate()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Eaten food added Successfully"));
    }

    @DeleteMapping("/profile/me/{localDate}/{foodId}")
    public ResponseEntity<?> deleteEatenFood(@PathVariable long foodId, @PathVariable(value = "localDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate postDate, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findUserById(currentUser.getId());
        Post post = postRepository.findByUser(user)
                .stream()
                .filter(p -> p.getDate().equals(postDate))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Date", "localDate", postDate));

        postService.deleteEatenFood(postDate, foodId);
        postService.calculate(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postDate}")
                .buildAndExpand(post.getDate()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Eaten food deleted Successfully"));
    }

    @PutMapping("/profile/me/{localDate}/{foodId}")
    public ResponseEntity<?> editEatenFoodWeight(@RequestBody @Valid FoodDto eatenFoodDto, @PathVariable long foodId, @PathVariable(value = "localDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate postDate, @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findUserById(currentUser.getId());
        Post post = postRepository.findByUser(user)
                .stream()
                .filter(p -> p.getDate().equals(postDate))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Date", "localDate", postDate));

        postService.editEatenFoodWeight(eatenFoodDto, postDate, foodId);
        postService.calculate(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postDate}")
                .buildAndExpand(post.getDate()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Weight of eaten food has been edited Successfully"));
    }
}