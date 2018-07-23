package pl.mh.reactapp.service;

import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mh.reactapp.domain.*;
import pl.mh.reactapp.payload.CalculatorDto;
import pl.mh.reactapp.payload.FoodDto;
import pl.mh.reactapp.payload.PostDto;
import pl.mh.reactapp.repository.EatenFoodRepository;
import pl.mh.reactapp.repository.FoodRepository;
import pl.mh.reactapp.repository.PostRepository;
import pl.mh.reactapp.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    EatenFoodRepository eatenFoodRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    @Autowired
    FoodService foodService;

    @Autowired
    private CaloricNeedsService caloricNeedsService;

    @Test
    public void calculatorTests() {
        CalculatorDto calculatorDto = new CalculatorDto();
        calculatorDto.setHeight(188);
        calculatorDto.setWeight(87);
        double testValue = caloricNeedsService.calculateBmi(calculatorDto.getWeight(), calculatorDto.getHeight());
        calculatorDto.setBmi(testValue);
        Assert.assertEquals(24.62, calculatorDto.getBmi(), 0.1);

        calculatorDto.setSex(Sex.MALE);
        calculatorDto.setAge(23);
        calculatorDto.setActivity(Activity.VERY_ACTIVE.getValue());
        double testValue1 = caloricNeedsService.calculateBmr(calculatorDto.getSex(), calculatorDto.getWeight(),
                calculatorDto.getHeight(), calculatorDto.getAge(), calculatorDto.getActivity());
        calculatorDto.setBmr(testValue1);
        Assert.assertEquals(3291, calculatorDto.getBmr(), 1);
        calculatorDto.setSex(Sex.FEMALE);
        double testValue2 = caloricNeedsService.calculateBmr(calculatorDto.getSex(), calculatorDto.getWeight(),
                calculatorDto.getHeight(), calculatorDto.getAge(), calculatorDto.getActivity());
        Assert.assertNotEquals(testValue, testValue2);
    }

    @Test
    public void testPostService() {

        final User dummyUser = userRepository.findUserById(1L);

        PostDto postDto = new PostDto(
                LocalDate.now(),
                Collections.emptyList(),
                0, 0, 0, 0);
        postService.createPost(postDto, dummyUser);

        Post post = postService.findUsersPostByDate(dummyUser, postDto.getLocalDate());

        Food exampleFood = foodRepository.findById(1L);
        Food exampleFood2 = foodRepository.findById(3L);

        assertNotNull(post);

        postService.addEatenFood(post.getId(), exampleFood.getId(), 100);
        postService.addEatenFood(post.getId(), exampleFood2.getId(), 100);
        List<EatenFood> eatenFoods = postService.getEatenFoodsByPost(post);
        assertEquals(2, eatenFoods.size());

        postDto.setCalories(postService.calculate(post).getCalories());
        double caloriesCopy = postDto.getCalories();
        assertNotEquals(caloriesCopy, 0);

        postService.deleteEatenFood(LocalDate.now(), 3);
        assertEquals(1, postService.getEatenFoodsByPost(post).size());
        postDto.setCalories(postService.calculate(post).getCalories());
        assertNotEquals(caloriesCopy, postDto.getCalories());

        assertNotEquals(caloriesCopy, postDto.getCalories());
        caloriesCopy = post.getCalories();

        EatenFood food = eatenFoods.get(0);
        double copyOfWeight = food.getQuantity();
        postService.editEatenFoodWeight(200, LocalDate.now(), 1);
        EatenFood copyOfEatenFood = postService.getEatenFoodsByPost(post).get(0);

        assertEquals(copyOfWeight*2, copyOfEatenFood.getQuantity(), 0.0);
        assertNotEquals(caloriesCopy, postDto.getCalories());
    }

    @Test
    public void foodServiceTest(){
        Category category = Category.MEAT;
        assertNotNull(foodService.findFoodsByCategory(category));

        List<Food> foods = foodRepository.findAll();
        FoodDto food = new FoodDto("Banana", 95, 1, 23.5, 0,100, Category.FRUITS);
        Food addedFood = foodService.createFood(food);
        assertNotEquals(foodRepository.findAll().size(), 10);

        Food food2 = foodRepository.findById(4);
        foodService.deleteFood(food2);
        assertEquals(foodRepository.findAll().size(), 10);

        String copyOfName = addedFood.getName();
        food.setName("bananek");
        foodService.updateFood(addedFood.getId(), food);
        assertNotEquals(copyOfName, foodRepository.findById(addedFood.getId()).get().getName());
    }

}