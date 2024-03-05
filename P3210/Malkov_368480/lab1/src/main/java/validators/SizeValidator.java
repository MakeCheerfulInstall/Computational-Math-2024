package validators;

public class SizeValidator implements IValidator<Short> {
    @Override
    public boolean test(Short val) {
        return val > 0;
    }
}
