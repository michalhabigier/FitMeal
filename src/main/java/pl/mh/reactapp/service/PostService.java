package pl.mh.reactapp.service;

import org.springframework.stereotype.Service;
import pl.mh.reactapp.domain.EatenFood;
import pl.mh.reactapp.domain.Food;
import pl.mh.reactapp.domain.Post;
import pl.mh.reactapp.domain.User;
import pl.mh.reactapp.payload.FoodDto;
import pl.mh.reactapp.payload.PostDto;
import pl.mh.reactapp.repository.EatenFoodRepository;
import pl.mh.reactapp.repository.FoodRepository;
import pl.mh.reactapp.repository.PostRepository;
import pl.mh.reactapp.repository.UserRepository;
import pl.mh.reactapp.util.AmountRounder;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final EatenFoodRepository eatenFoodRepository;

    private final FoodRepository foodRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, EatenFoodRepository eatenFoodRepository,
                       FoodRepository foodRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.eatenFoodRepository = eatenFoodRepository;
        this.foodRepository = foodRepository;
    }

    public Post createPost(@Valid PostDto postDto, User user) {
        Post post = new Post();
        post.setProteins(postDto.getProteins());
        post.setCarbohydrates(postDto.getCarbohydrates());
        post.setFat(postDto.getFat());
        post.setUser(user);
        post.setDate(LocalDate.now());
        post.setCalories(postDto.getCalories());
        return postRepository.save(post);
    }

    public EatenFood addEatenFood(long postId, long foodId, FoodDto eatenFoodDto){
        Post post = postRepository.findById(postId);
        Food food = foodRepository.findById(foodId);

        EatenFood eatenFood = new EatenFood();

        Double portion = eatenFoodDto.getPortion()/100;

        eatenFood.setQuantity(eatenFoodDto.getPortion());
        eatenFood.setName(food.getName());
        eatenFood.setCarbohydrates(AmountRounder.round(food.getCarbohydrates()*portion));
        eatenFood.setProteins(AmountRounder.round(food.getProteins()*portion));
        eatenFood.setFat(AmountRounder.round(food.getFat()*portion));
        eatenFood.setTotalCalories(AmountRounder.round(food.getTotalCalories()*portion));
        eatenFood.setPost(post);
        eatenFood.setFood(food);

        return eatenFoodRepository.save(eatenFood);
    }

    public void deleteEatenFood(LocalDate localDate, long foodId){
        Post post = postRepository.findPostByDate(localDate);
        EatenFood food = eatenFoodRepository.findByPostAndFoodId(post, foodId);
        eatenFoodRepository.delete(food);
    }

    public EatenFood editEatenFoodWeight(@Valid FoodDto eatenFoodDto, LocalDate localDate, long foodId){
        Post post = postRepository.findPostByDate(localDate);
        EatenFood eatenFood = eatenFoodRepository.findByPostAndFoodId(post, foodId);
        Food food = foodRepository.findById(foodId);
        Double portion = eatenFoodDto.getPortion()/100;
        eatenFood.setQuantity(eatenFoodDto.getPortion());
        eatenFood.setName(food.getName());
        eatenFood.setCarbohydrates(AmountRounder.round(food.getCarbohydrates()*portion));
        eatenFood.setProteins(AmountRounder.round(food.getProteins()*portion));
        eatenFood.setFat(AmountRounder.round(food.getFat()*portion));
        eatenFood.setTotalCalories(AmountRounder.round(food.getTotalCalories()*portion));
        return eatenFoodRepository.save(eatenFood);
    }

    public PostDto calculate(Post post){
        PostDto postDto = new PostDto();
        List<EatenFood> eatenFoods = eatenFoodRepository.findByPost(post);
        postDto.setEatenFoods(eatenFoods);
        Double totalCalories = eatenFoods.stream().mapToDouble(EatenFood::getTotalCalories).sum();
        Double totalCarbohydrates = eatenFoods.stream().mapToDouble(EatenFood::getCarbohydrates).sum();
        Double totalFat = eatenFoods.stream().mapToDouble(EatenFood::getFat).sum();
        Double totalProteins = eatenFoods.stream().mapToDouble(EatenFood::getProteins).sum();
        postDto.setCalories(AmountRounder.round(totalCalories));
        postDto.setCarbohydrates(AmountRounder.round(totalCarbohydrates));
        postDto.setFat(AmountRounder.round(totalFat));
        postDto.setProteins(AmountRounder.round(totalProteins));
        return postDto;
    }

}
