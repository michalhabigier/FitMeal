package pl.mh.reactapp.domain;

public enum Activity {
    SEDENTARY(1.25),
    LIGHTLY_ACTIVE(1.3),
    MODERATELY_ACTIVE(1.5),
    VERY_ACTIVE(1.7),
    EXTREMELY_ACTIVE(2.0);

    private double value;

    Activity(final double value) {
        this.value = value;
    }

    public static Activity fromValue(final double value) {
        Activity match = null;
        for (final Activity activityLevel : Activity.values()) {
            if (activityLevel.getValue() == value) {
                match = activityLevel;
            }
        }
        return match;
    }

    public double getValue() {
        return value;
    }
}
