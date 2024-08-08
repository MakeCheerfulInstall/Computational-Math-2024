package Computational.math.Utils;

import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.ArrayList;

public class FunctionalTable {
    private Double[] xArr;
    private Double[] yArr;

    public FunctionalTable(Double[] xArr,Double[] yArr){
        this.xArr = xArr;this.yArr=yArr;
    }

    @Override
    public String toString() {
        String toReturn = "";
        try {
            JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
            ArrayList<String> xArrayList = new ArrayList<>();
            xArrayList.add("x");
            for (Double x : xArr) {
                xArrayList.add(String.format("%.3f",x));
            }
            ArrayList<String> yArrayList = new ArrayList<>();
            yArrayList.add("y");
            for (Double y : yArr) {
                yArrayList.add(String.format("%.3f",y));
            }
            builder.columns(xArrayList.toArray(new String[0]));
            builder.row(yArrayList);

            toReturn = builder.getTable().toStringHorizontal();
        }catch (MalformedTableException e){
            System.err.println("Can't create table for printing: " + e.getMessage());
        }

        return toReturn;
    }

    public void printTable(){
        try {
            JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
            ArrayList<String> xArrayList = new ArrayList<>();
            xArrayList.add("x");
            for (Double x : xArr) {
                xArrayList.add(String.format("%.3f",x));
            }
            ArrayList<String> yArrayList = new ArrayList<>();
            yArrayList.add("y");
            for (Double y : yArr) {
                yArrayList.add(String.format("%.3f",y));
            }
            builder.columns(xArrayList.toArray(new String[0]));
            builder.row(yArrayList);

            System.out.println(builder.getTable().toStringHorizontal());
        }catch (MalformedTableException e){
            System.err.println("Can't create table for printing: " + e.getMessage());
        }
    }
    public int dimension(){
        if(xArr.length != yArr.length)
            throw new RuntimeException("Это баг :D");
        return xArr.length;
    }
    public Double[] getxArr() {
        return xArr;
    }

    public void setxArr(Double[] xArr) {
        this.xArr = xArr;
    }

    public Double[] getyArr() {
        return yArr;
    }

    public void setyArr(Double[] yArr) {
        this.yArr = yArr;
    }
}
