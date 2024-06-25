package Lab1;

import java.io.IOException;
/**
 * Интерфейс, который используется для получения матрицы из разных источников
 * @author Анастасия Коляда
 *
 */
public interface IGetMatrix {
	Matrix GetMatrix() throws IOException;
}
