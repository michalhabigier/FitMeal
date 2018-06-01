package pl.mh.reactapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.domain.UserDetails;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.exception.UnauthorizedUserException;
import pl.mh.reactapp.payload.ApiResponse;
import pl.mh.reactapp.payload.UserProfile;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;
import pl.mh.reactapp.util.AgeCalculator;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/profile/me/editDetails")
    public ResponseEntity<?> editCurrentUserDetails(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UserDetails userDetails){
        //User user = userRepository.findById(currentUser.getId()).orElseThrow(() ->
        //        new UnauthorizedUserException("Request for unauthorized user"));
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

    //TODO Weight Entity
    @GetMapping("/profile/{username}")
    public UserProfile getByUsername(@PathVariable String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new ResourceNotFoundException("User", "username", username));

        int age = AgeCalculator.calculateAge(user.getDateOfBirth(), LocalDate.now());

        return new UserProfile(user.getUsername(), age, user.getCreatedDate().toLocalDate(), user.getUserDetails().getCurrentWeight(),
                user.getUserDetails().getHeight(), user.getUserDetails().getBodyFat(), user.getUserDetails().getWaistLevel());
    }

    //TODO Post Entity
    //@GetMapping("/profile/{username}/posts/{id} wyświetlanie postów


}
