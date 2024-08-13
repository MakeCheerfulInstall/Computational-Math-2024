package Computational.math;

public enum MethodName {
    HALF_DIVISION(1),
    SECANT_METHOD(2),
    SIMPLE_ITERATION(3),
    NEWTON_METHOD(4);

    private int value;
    MethodName(int value) {
        this.value = value;
    }
}
