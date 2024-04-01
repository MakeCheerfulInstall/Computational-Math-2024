package validators;

public class AccuracyValidator implements IValidator<Double> {
    @Override
    public boolean test(Double val) {
        return val > 0;
    }
}
