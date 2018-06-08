package pl.mh.reactapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.mh.reactapp.domain.Category;
import pl.mh.reactapp.domain.Food;
import pl.mh.reactapp.domain.Role;
import pl.mh.reactapp.domain.RoleName;
import pl.mh.reactapp.repository.FoodRepository;
import pl.mh.reactapp.repository.RoleRepository;

@Component
public class DataBaseLoader implements CommandLineRunner {

    private final FoodRepository foodRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public DataBaseLoader(FoodRepository foodRepository, RoleRepository roleRepository) {
        this.foodRepository = foodRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.foodRepository.save(new Food("Orzeszki ziemne", 596, 27, 14, 48, Category.FATS));
        this.foodRepository.save(new Food("Banan", 95, 1, 23.5, 0.3, Category.FRUITS));
        this.foodRepository.save(new Food("Kajzerka", 296, 7.5, 59.4, 3.6, Category.CEREALS));
        this.foodRepository.save(new Food("Dorsz świeży", 78, 17.7, 0, 0.7, Category.MEAT));
        this.foodRepository.save(new Food("Mleko 2%", 51, 3.4, 4.9, 2, Category.FATS));
        this.foodRepository.save(new Food("Oliwa z oliwek", 897, 0.2, 0, 99, Category.FATS));
        this.foodRepository.save(new Food("Ser twarogowy półtłusty", 132, 18.7, 3.7, 4.7, Category.DAIRY));
        this.foodRepository.save(new Food("Snickers, baton", 509, 9.7, 52.6, 28.9, Category.SWEETS));
        this.foodRepository.save(new Food("Jajo kurze", 139, 12.5, 0.6, 9.7, Category.DAIRY));
        this.foodRepository.save(new Food("Kurczak, mięso z piersi bez skóry", 99, 21.5, 0, 1.3, Category.MEAT));
        this.roleRepository.save(new Role(RoleName.ROLE_USER));
        this.roleRepository.save(new Role(RoleName.ROLE_ADMIN));
    }
}