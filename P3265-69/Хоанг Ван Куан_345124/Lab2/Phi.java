public class Phi {
    private double lambda;
    NonlinearEquation f;
    public Phi(double a, double b, NonlinearEquation f){
        this.f = f;
        lambda = - 1 / (Math.max(Math.abs(f.d(1,a)), Math.abs(f.d(1,b))));
    }
    public double apply(double x){
        return x + lambda * f.apply(x);
    }
    public double dphi(double x){
        return 1 + lambda * f.apply(x);
    }
}
