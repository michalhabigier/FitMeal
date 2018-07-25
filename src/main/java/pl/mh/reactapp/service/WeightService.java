package pl.mh.reactapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.domain.Weight;
import pl.mh.reactapp.exception.ResourceNotFoundException;
import pl.mh.reactapp.payload.ApiResponse;
import pl.mh.reactapp.payload.WeightDto;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.repository.WeightRepository;
import pl.mh.reactapp.security.CurrentUser;
import pl.mh.reactapp.security.UserPrincipal;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class WeightService {

    private final UserRepository userRepository;
    private final WeightRepository weightRepository;

    public WeightService(UserRepository userRepository, WeightRepository weightRepository) {
        this.userRepository = userRepository;
        this.weightRepository = weightRepository;
    }

    public void saveCurrentWeight(User user, @Valid WeightDto weightDto){
        Weight weight = new Weight(weightDto.getDate(), weightDto.getWeight(), user);
        weightRepository.save(weight);
    }

    public void deleteWeightByDate(LocalDate weightDate, User user){
        List<Weight> weightList = weightRepository.findByUser(user);
        Weight weight = weightList.stream()
                .filter(p -> p.getDate().equals(weightDate))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Date", "localDate", weightDate));
        weightRepository.delete(weight);
    }

    public Weight getCurrentWeight(User user){
        return weightRepository.findByUser(user)
                .stream()
                .max(Comparator.comparing(Weight::getDate))
                .get();
    }

    public Weight getHighestWeight(User user){
        return weightRepository.findByUser(user)
                .stream()
                .max(Comparator.comparing(Weight::getWeight))
                .get();
    }

    public Weight getLowestWeight(User user){
        return weightRepository.findByUser(user)
                .stream()
                .min(Comparator.comparing(Weight::getWeight))
                .get();
    }

}
