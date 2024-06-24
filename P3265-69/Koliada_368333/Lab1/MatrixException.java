package Lab1;
/**
 * Ошибюка, возникающая при расчете матрицы
 * 
 * @author Анастасия Коляда
 *
 */
@SuppressWarnings("serial")
public class MatrixException extends RuntimeException {
	public MatrixException(String message) {
		super(message);
	}
}
