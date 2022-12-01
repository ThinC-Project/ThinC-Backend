package com.thincbackend.nugu;

public enum FireStrength {
    UP("강", "라면"),
    MIDDLE("중", "제육볶음"),
    DOWN("약", "계란후라이");

    private String strength;
    private String food;


    FireStrength(String strength, String food) {
        this.strength = strength;
        this.food = food;
    }

    public String getStrength() {
        return strength;
    }

    public String getFood() {
        return food;
    }
}
