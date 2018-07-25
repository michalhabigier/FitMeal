package pl.mh.reactapp.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.domain.UserDetails;
import pl.mh.reactapp.domain.Weight;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.payload.*;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.repository.WeightRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;
import pl.mh.reactapp.service.CaloricNeedsService;
import pl.mh.reactapp.service.WeightService;
import pl.mh.reactapp.util.AgeCalculator;
import pl.mh.reactapp.util.ObjectMapperUtils;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserRepository userRepository;

    private final WeightRepository weightRepository;

    private final CaloricNeedsService caloricNeedsService;

    private final WeightService weightService;

    public UserController(UserRepository userRepository, WeightRepository weightRepository,
                          CaloricNeedsService caloricNeedsService, WeightService weightService) {
        this.userRepository = userRepository;
        this.weightRepository = weightRepository;
        this.caloricNeedsService = caloricNeedsService;
        this.weightService = weightService;
    }

    @PutMapping("/profile/me/editDetails")
    public ResponseEntity<?> editCurrentUserDetails(@CurrentUser UserPrincipal currentUser,
                                                    @Valid @RequestBody UserDetails userDetails) {
        User user = userRepository.findUserById(currentUser.getId());
        user.getUserDetails().setBodyFat(userDetails.getBodyFat());
        user.getUserDetails().setCurrentWeight(userDetails.getCurrentWeight());
        user.getUserDetails().setHeight(userDetails.getHeight());
        user.getUserDetails().setWaistLevel(userDetails.getWaistLevel());
        userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/profile/{username}")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(
                new ApiResponse(true, "User details has been changed successfully"));
    }

    @GetMapping("/profile/{username}")
    public UserProfile getByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        int age = AgeCalculator.calculateAge(user.getDateOfBirth(), LocalDate.now());

        return new UserProfile(user.getUsername(), age, user.getSex(), user.getCreatedDate().toLocalDate(),
                user.getUserDetails().getCurrentWeight(), user.getUserDetails().getHeight(),
                user.getUserDetails().getBodyFat(), user.getUserDetails().getWaistLevel());
    }

    @GetMapping("/profile/me")
    public CurrentUserInfo getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new CurrentUserInfo(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail(), currentUser.getSex());
    }

    @PostMapping("/calculate/bmi")
    public CalculatorDto calculateCaloricNeeds(@Valid @RequestBody CalculatorDto calculatorDto){
        return caloricNeedsService.calculateBmi(calculatorDto);
    }

    @PostMapping("/calculate/bmr")
    public CalculatorDto calculateBmr(@Valid @RequestBody CalculatorDto calculatorDto){
        return caloricNeedsService.calculateBmr(calculatorDto);
    }

    @GetMapping("/profile/{username}/weightHistory")
    public List<WeightDto> WeightsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", username));

        List<Weight> weights = weightRepository.findByUser(user);

        return ObjectMapperUtils.mapAll(weights, WeightDto.class);
    }

    @DeleteMapping("/profile/me/weightHistory/{weightDate}")
    public ResponseEntity<?> deleteEatenFood(@PathVariable(value = "weightDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                         LocalDate weightDate, @CurrentUser UserPrincipal currentUser){
        User user = userRepository.findUserById(currentUser.getId());
        weightService.deleteWeightByDate(weightDate, user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/profile/{username}/weightHistory")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(
                new ApiResponse(true, "User weight has been successfully deleted"));
    }

    @PostMapping("/profile/me/addWeight")
    public ResponseEntity<?> saveCurrentWeight(@CurrentUser UserPrincipal currentUser,
                                               @Valid @RequestBody WeightDto weightDto) {

        User user = userRepository.findUserById(currentUser.getId());
        weightService.saveCurrentWeight(user, weightDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/profile/{username}/weightHistory")
                .buildAndExpand(user.getUsername()).toUri();
        return ResponseEntity.created(location).body(
                new ApiResponse(true, "User weight has been successfully saved"));
    }


}
