package lab4.entity;

public class DotCollection {
    private static Dot[] dots;

    public static Dot[] getDots() {
        return dots;
    }

    public static void setDots(Dot[] dots) {
        DotCollection.dots = dots;
    }

    public static double minX() {
        double minX = Double.MAX_VALUE;
        for (Dot dot : dots) {
            if (dot.getX() < minX) minX = dot.getX();
        }
        return minX;
    }

    public static double maxX() {
        double maxX = Double.MIN_VALUE;
        for (Dot dot : dots) {
            if (dot.getX() > maxX) maxX = dot.getX();
        }
        return maxX;
    }
}
