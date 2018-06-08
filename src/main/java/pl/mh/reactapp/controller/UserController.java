package pl.mh.reactapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.domain.UserDetails;
import pl.mh.reactapp.domain.Weight;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.payload.ApiResponse;
import pl.mh.reactapp.payload.UserProfile;
import pl.mh.reactapp.payload.WeightDto;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.repository.WeightRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;
import pl.mh.reactapp.util.AgeCalculator;
import pl.mh.reactapp.util.ObjectMapperUtils;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    private final WeightRepository weightRepository;

    public UserController(UserRepository userRepository, WeightRepository weightRepository) {
        this.userRepository = userRepository;
        this.weightRepository = weightRepository;
    }

    @PutMapping("/profile/me/editDetails")
    public ResponseEntity<?> editCurrentUserDetails(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UserDetails userDetails) {
        User user = userRepository.findUserById(currentUser.getId());
        user.getUserDetails().setBodyFat(userDetails.getBodyFat());
        user.getUserDetails().setCurrentWeight(userDetails.getCurrentWeight());
        user.getUserDetails().setHeight(userDetails.getHeight());
        user.getUserDetails().setWaistLevel(userDetails.getWaistLevel());
        userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/profile/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User details has been changed successfully"));
    }

    @GetMapping("/profile/{username}")
    public UserProfile getByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        int age = AgeCalculator.calculateAge(user.getDateOfBirth(), LocalDate.now());

        return new UserProfile(user.getUsername(), age, user.getCreatedDate().toLocalDate(), user.getUserDetails().getCurrentWeight(),
                user.getUserDetails().getHeight(), user.getUserDetails().getBodyFat(), user.getUserDetails().getWaistLevel());
    }

    @GetMapping("/profile/{username}/weightHistory")
    public List<WeightDto> WeightsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        //Weight weight = weightRepository.findByUser(user)
        //       .stream()
        //        .reduce(BinaryOperator.maxBy(Comparator.comparing(Weight::getDate))).get();

        List<Weight> weights = weightRepository.findByUser(user);

        return ObjectMapperUtils.mapAll(weights, WeightDto.class);
    }

    //TODO Post Entity
    //@GetMapping("/profile/{username}/posts/{id} wyświetlanie postów

    @PostMapping("/profile/me/addWeight")
    public ResponseEntity<?> saveCurrentWeight(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody WeightDto weightDto) {
        User user = userRepository.findUserById(currentUser.getId());
        Weight weight = new Weight(weightDto.getDate(), weightDto.getWeight(), user);
        weightRepository.save(weight);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/profile/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "User weight has been successfully saved"));
    }

    //@PostMapping("/profile/me/newPost")
    //public ResponseEntity<?> addPost(@CurrentUser )
}
