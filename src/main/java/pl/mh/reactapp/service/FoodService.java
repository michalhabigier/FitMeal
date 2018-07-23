package pl.mh.reactapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.Category;
import pl.mh.reactapp.domain.Food;
import pl.mh.reactapp.payload.FoodDto;
import pl.mh.reactapp.repository.FoodRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FoodService {

    private final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food createFood(FoodDto foodDto) {
        Food food = new Food();
        food.setName(foodDto.getName());
        food.setCategory(foodDto.getCategory());
        food.setProteins(foodDto.getProteins());
        food.setCarbohydrates(foodDto.getCarbohydrates());
        food.setProteins(foodDto.getProteins());
        food.setTotalCalories(foodDto.getTotalCalories());
        food.setCategory(foodDto.getCategory());
        log.debug("Created new ingredient \"{}\"", food.getName());
        return foodRepository.save(food);
    }

    public void updateFood(long foodId, FoodDto foodDto) {
            Food food = foodRepository.findById(foodId);
            food.setName(foodDto.getName());
            food.setCategory(foodDto.getCategory());
            food.setProteins(foodDto.getProteins());
            food.setCarbohydrates(foodDto.getCarbohydrates());
            food.setProteins(foodDto.getProteins());
            food.setTotalCalories(foodDto.getTotalCalories());
            food.setCategory(foodDto.getCategory());
            foodRepository.save(food);
        log.debug("Updated new ingredient \"{}\"", food.getName());
    }

    public void deleteFood(Food food){
        foodRepository.delete(food);
        log.debug("Ingredient deleted \"{}\"", food.getName());

    }

    public List<Food> findFoodsByCategory(Category category) {
        return foodRepository.findAll()
                .stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }
}
