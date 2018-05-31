package pl.mh.reactapp.domain;

public enum Category {
    VEGETABLES("vegetables"),
    FRUITS("fruits"),
    MEAT("meat"),
    SWEETS("sweets"),
    FATS("fats"),
    CEREALS("cereals"),
    ALCOHOL("alcohol"),
    DAIRY("dairy");

    private String s;

    Category(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
