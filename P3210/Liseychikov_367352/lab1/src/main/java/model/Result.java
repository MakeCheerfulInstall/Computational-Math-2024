package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Result {
    @Getter
    @Setter
    private ArrayList<Double> residuals = new ArrayList<>();
    private final ArrayList<ArrayList<Double>> iters = new ArrayList<>();
    @Getter
    private final ArrayList<Double> result = new ArrayList<>();
    private final ArrayList<ArrayList<Double>> e = new ArrayList<>();

    public void addIter(double[] iter) {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (double it : iter) {
            arrayList.add(it);
        }
        iters.add(arrayList);
    }

    public String getTable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("№\t|");
        for (int i = 0; i < iters.get(0).size(); i++) {
            stringBuilder.append("x").append(i + 1).append("\t\t\t\t|");
        }
        int len = stringBuilder.length();
        stringBuilder.delete(len - 2, len - 1);
        for (int i = 0; i < iters.get(0).size(); i++) {
            stringBuilder.append("eps").append(i + 1).append("\t\t|");
        }
        stringBuilder.append("\n");
        for (int i = 0; i < iters.size(); i++) {
            stringBuilder.append(i + 1).append("\t|");
            for (Double it : iters.get(i)) {
                stringBuilder.append(String.format("%.6f", it)).append("\t\t|");
            }
            stringBuilder.append(" ");
            try {
                if (!e.isEmpty() && !e.get(i).isEmpty()) {
                    for (Double it : e.get(i)) {
                        stringBuilder.append(String.format("%.6f", it)).append("\t|");
                    }
                }
            } catch (Exception ignored) {
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void setResult(double[] res) {
        for (double it : res) {
            result.add(it);
        }
    }

    public void addE(ArrayList<Double> e) {
        this.e.add(e);
    }
}