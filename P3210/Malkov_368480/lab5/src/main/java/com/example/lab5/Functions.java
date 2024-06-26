package com.example.lab5;

import lombok.Getter;

import java.util.function.Function;

public enum Functions{
    SINX("sin(x)", Math::sin),
    COSX("cos(x)", Math::cos),
    TEST("test", x -> x+1);
    @Getter
    private final String description;
    @Getter
    private final Function<Double, Double> function;

    Functions(String description, Function<Double, Double> function) {
        this.description = description;
        this.function = function;
    }
    public static Functions getByDesc(String descr) {
        for (Functions e : values()) {
            if (e.getDescription().equals(descr)) {
                return e;
            }
        }
        return null;
    }
}
