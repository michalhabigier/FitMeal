package pl.mh.reactapp.payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfile {

    private String username;

    private int age;

    private LocalDate joinedAt;

    private double currentWeight;

    private int height;

    private double bodyFat;

    private double waistLevel;

    public UserProfile(String username, int age, LocalDate joinedAt, double currentWeight, int height,
                       double bodyFat, double waistLevel) {
        this.username = username;
        this.age = age;
        this.joinedAt = joinedAt;
        this.currentWeight = currentWeight;
        this.height = height;
        this.bodyFat = bodyFat;
        this.waistLevel = waistLevel;
    }
}
