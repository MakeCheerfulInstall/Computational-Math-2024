package Lab2;

/**
 * Ошибка, возникающая при выполнении расчетных действий
 */
public class CalcErrorException extends RuntimeException{
    public  CalcErrorException(String error) {
        super(error);
    }
}

