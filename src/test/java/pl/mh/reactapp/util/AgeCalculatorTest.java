package pl.mh.reactapp.util;

import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AgeCalculatorTest {

    @Test
    public void calculateAge() {
        LocalDate birthDate = LocalDate.of(1995, 07, 20);
        int actualAge = AgeCalculator.calculateAge(birthDate, LocalDate.now());
        Assert.assertEquals(23, actualAge);
    }
}