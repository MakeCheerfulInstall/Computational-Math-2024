package lab1;

import java.util.Scanner;

public interface IContext {
    void print(String s);
    Scanner getReader();
    void setAccuracy(double v);
    double getAccuracy();
}
