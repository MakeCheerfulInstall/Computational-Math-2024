package com.example.lab6;

import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum Functions{
    FIRST("y' = (x + y) / 2", (x, y) -> (x + y) / 2, (x0,y0,x) -> (y0 + x0 + 2) / Math.exp(x0 / 2) * Math.exp(x / 2) - x - 2),
    SECOND("y' = -(2y+1)*tg(x)", (x, y) -> -(2 * y + 1) / Math.tan(x), (x0,y0,x) -> (y0 + 0.5) * 2 * Math.pow(Math.sin(x0), 2) / (2 * Math.pow(Math.sin(x), 2)) - 0.5);
    @Getter
    private final String description;
    @Getter
    private final BiFunction<Double, Double, Double> function;
    @Getter
    private final TriFunction<Double,Double, Double, Double> trueFunction;

    Functions(String name, BiFunction<Double, Double, Double> function,
              TriFunction<Double,Double, Double, Double> trueFunction) {
        this.description = name;
        this.function = function;
        this.trueFunction = trueFunction;
    }

    public static Functions getByDesc(String descr) {
        for (Functions e : values()) {
            if (e.getDescription().equals(descr)) {
                return e;
            }
        }
        return null;
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    public double getTrueValue(double a, double b, double c) {
        return this.getTrueFunction().apply(a, b, c);
    }
}
