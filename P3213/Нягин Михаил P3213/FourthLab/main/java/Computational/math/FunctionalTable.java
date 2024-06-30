package Computational.math;

import lombok.Data;

@Data
public class FunctionalTable {
    double[][] table;
    public FunctionalTable(double[][] data){
        this.table = data;
    }
    public double getSumXi(){
        var sum = 0d;
        for (int i = 0; i < table[0].length; i++) {
            sum+=table[0][i];
        }
        return sum;
    }
    public double getSumXiWithPow(int powN){
        var sum = 0d;
        for (int i = 0; i < table[0].length; i++) {
            sum+=Math.pow(table[0][i],powN);
        }
        return sum;
    }
    public double getSumYi(){
        var sum = 0d;
        for (int i = 0; i < table[0].length; i++) {
            sum+=table[1][i];
        }
        return sum;
    }
    public double getSumYiWithPow(int powN){
        var sum = 0d;
        for (int i = 0; i < table[0].length; i++) {
            sum+=Math.pow(table[1][i],powN);
        }
        return sum;
    }



    public double getMultiplyXandYWithPows(int powX,int powY){
        var sum = 0d;
        for (int i = 0; i < table[0].length; i++) {
            sum += Math.pow(table[0][i],powX)*Math.pow(table[1][i],powY);
        }
        return sum;
    }
    public double getMultiplyXandYWithPows(){
        var sum = 0d;
        for (int i = 0; i < table[0].length; i++) {
            sum += table[0][i]*table[1][i];
        }
        return sum;
    }
}
