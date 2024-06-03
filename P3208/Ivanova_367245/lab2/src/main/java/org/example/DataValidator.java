package org.example;

public class DataValidator {
    Functions functions = new Functions();


    public boolean checkInterval(double a, double b, int number) {
        boolean rootFlag = false;

        for (double i = a; i <= b - 0.1; i = i + 0.1)
            if (functions.getFunction(i, number) > 0 && functions.getFunction(i + 0.1, number) < 0 || functions.getFunction(i, number) < 0 &&
                    functions.getFunction(i + 0.1, number) > 0) {
                rootFlag = true;
                break;
            }

        if (functions.getFunction(a, number) * functions.getFunction(b, number) < 0 && !rootFlag)
            rootFlag = true;

        return rootFlag;
    }

    public int countIntervalRoots(double a, double b, int number) {
        int roots = 0;
        for (double i = a; i <= b; i += 0.1) {
            if (functions.getFunction(i, number) > 0 && functions.getFunction(i + 0.1, number) < 0 || functions.getFunction(i, number) < 0 &&
                    functions.getFunction(i + 0.1, number) > 0) {
                roots++;
            }
        }
        return roots;
    }

}
