import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class SystemNonlinearEquation {
    private int functionNum;
    public SystemNonlinearEquation(int functionNum){
        this.functionNum = functionNum;
    }
    public double f1(double x, double y){
        switch (functionNum){
            case 1: return 0.3 - 0.1 * x * x - 0.2 * y * y - x;
            default: return Math.sin(y + 0.5) - x - 1;
        }
    }
    public double f2(double x, double y){
        switch (functionNum){
            case 1: return 0.7 - 0.2 * x * x - 0.1 * x * y - y;
            default: return y + Math.cos(x-2);
        }
    }
    public double g_x(double x, double y){
        switch (functionNum){
            case 1: return 0.3 - 0.1 * x * x - 0.2 * y * y;
            default: return Math.sin(y + 0.5) - 1;
        }
    }
    public double g_x_positive(double y){
        switch (functionNum){
            case 1: return Math.pow((28 - 2 * y * y),0.5) - 5;
            default: return Math.sin(y + 0.5) - 1;
        }
    }
    public double g_x_negative(double y){
        switch (functionNum){
            case 1: return -Math.pow((28 - 2 * y * y),0.5) - 5;
            default: return Math.sin(y + 0.5) - 1;
        }
    }
    public double g_y(double x, double y){
        switch (functionNum){
            case 1: return 0.7 - 0.2 * x * x - 0.1 * x * y;
            default: return -Math.cos(x - 2);
        }
    }
    public double dx(double x, double y, int funcNum) {
        double h = 0.00001;
        if(funcNum == 1) return (g_x(x + h, y) - g_x(x,y)) / h;
        else return (g_y(x + h, y) - g_y(x,y)) / h;
    }
    public double dy(double x, double y, int funcNum){
        double h = 0.00001;
        if(funcNum == 1) return (g_x(x, y + h) - g_x(x,y)) / h;
        else return (g_y(x, y + h) - g_y(x,y)) / h;
    }
}