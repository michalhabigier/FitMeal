package pl.mh.reactapp.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class UserDetails {

    private int height;

    private double currentWeight;

    private double bodyFat;

    private double waistLevel;

    public UserDetails(int height, double currentWeight, double bodyFat, double waistLevel) {
        this.height = height;
        this.currentWeight = currentWeight;
        this.bodyFat = bodyFat;
        this.waistLevel = waistLevel;
    }

    public UserDetails() {
    }
}
