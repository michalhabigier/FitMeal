package pl.mh.reactapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.Food;
import pl.mh.reactapp.repository.FoodRepository;


@Service
public class FoodService {

    private final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public void createFood(){
        Food food = new Food();
        food.setName(food.getName());
        food.setCategory(food.getCategory());
        food.setProteins(food.getProteins());
        food.setCarbohydrates(food.getCarbohydrates());
        food.setProteins(food.getProteins());
        food.setTotalCalories(food.getTotalCalories());
        foodRepository.save(food);
        log.debug("Created new ingredient \"{}\"", food.getName());
    }

    public void updateFood(Food food){
        food.setName(food.getName());
        food.setCategory(food.getCategory());
        food.setProteins(food.getProteins());
        food.setCarbohydrates(food.getCarbohydrates());
        food.setProteins(food.getProteins());
        food.setTotalCalories(food.getTotalCalories());
        foodRepository.save(food);
        log.debug("Updated new ingredient \"{}\"", food.getName());
    }
}
