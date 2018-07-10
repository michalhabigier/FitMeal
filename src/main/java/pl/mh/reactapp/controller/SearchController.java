package pl.mh.reactapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.mh.reactapp.config.EnumConverter;
import pl.mh.reactapp.domain.Category;
import pl.mh.reactapp.domain.Food;
import pl.mh.reactapp.repository.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/search")
public class SearchController {

    private final FoodRepository foodRepository;

    public SearchController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping(params = "category")
    public ResponseEntity<List<Food>> findByCategory(@RequestParam(name = "category", required = false) Category category) {
        List<Food> foods = foodRepository.findAll()
                .stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name) {
        return new ResponseEntity<>(foodRepository.findByNameContainingIgnoreCase(name), HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Category.class, new EnumConverter<>(Category.class));
    }

}
