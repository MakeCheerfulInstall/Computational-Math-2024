import random


class Matrix:
    def __init__(self, row_count: int, column_count: int, data: list):
        self.__row_count = row_count
        self.__column_count = column_count
        self.__data = data

    def get_row_count(self) -> int:
        return self.__row_count

    def get_column_count(self) -> int:
        return self.__column_count

    def get_data(self) -> list:
        return self.__data

    def __str__(self) -> str:
        return "\n".join(map(str, self.get_data()))

    def __add__(self, other: 'Matrix') -> 'Matrix':
        if self.get_row_count() != other.get_row_count() or self.get_column_count() != other.get_column_count():
            raise ValueError("Matrices must have the same dimensions for addition")

        result_data: list = [[self.get_data()[i][j] + other.get_data()[i][j]
                              for j in range(self.get_column_count())]
                             for i in range(self.get_row_count())]

        return Matrix(self.get_row_count(), self.get_column_count(), result_data)

    def __sub__(self, other: 'Matrix') -> 'Matrix':
        if self.get_row_count() != other.get_row_count() or self.get_column_count() != other.get_column_count():
            raise ValueError("Matrices must have the same dimensions for addition")

        result_data: list = [[self.get_data()[i][j] - other.get_data()[i][j]
                              for j in range(self.get_column_count())]
                             for i in range(self.get_row_count())]

        return Matrix(self.get_row_count(), self.get_column_count(), result_data)

    def __multiply_with_number(self, other: int) -> 'Matrix':
        result_data: list = [[self.get_data()[i][j] * other
                              for j in range(self.get_column_count())]
                             for i in range(self.get_row_count())]

        return Matrix(self.get_row_count(), self.get_column_count(), result_data)

    def __multiply_with_matrix(self, other: 'Matrix') -> 'Matrix':
        if self.get_column_count() != other.get_row_count():
            raise ValueError(
                "Number of columns in the first matrix must be equal to the number of rows in the second matrix")

        result_data: list = []
        for i in range(self.get_row_count()):
            current_row: list = []
            for j in range(other.get_column_count()):
                current_sum: int = 0
                for k in range(self.get_column_count()):
                    current_sum += self.get_data()[i][k] * other.get_data()[k][j]
                current_row.append(current_sum)
            result_data.append(current_row)

        return Matrix(self.get_row_count(), other.get_column_count(), result_data)

    def __mul__(self, other) -> 'Matrix':
        if isinstance(other, int):
            return self.__multiply_with_number(other)

        elif isinstance(other, Matrix):
            return self.__multiply_with_matrix(other)

        else:
            raise TypeError("Unsupported operand type for multiplication")

    def __rmul__(self, other):
        return self * other

    def get_max_by_module(self) -> float:
        max_val: float = float("-inf")
        for row in self.get_data():
            for num in row:
                if abs(num) > max_val:
                    max_val = abs(num)
        return max_val

    @staticmethod
    def generate_result(dimension: int) -> 'Matrix':
        result_data: list = [[random.randint(-1000, 1000)]
                             for j in range(dimension)]

        return Matrix(dimension, 1, result_data)

    @staticmethod
    def get_initial(dimension: int) -> 'Matrix':
        result_data: list = [[0]
                             for j in range(dimension)]

        return Matrix(dimension, 1, result_data)


class SquareMatrix(Matrix):
    def __init__(self, dimension: int, data: list):
        super().__init__(dimension, dimension, data)

    @staticmethod
    def generateMatrix(dimension: int) -> 'SquareMatrix':
        result_data: list = [[random.randint(-50, 50)
                              for j in range(dimension)]
                             for i in range(dimension)]
        for i in range(dimension):
            result_data[i][i] = sum(map(abs, result_data[i]))

        return SquareMatrix(dimension, result_data)

    def __check_row_diagonal_dominance(self, row: list, idx: int) -> bool:
        row_sum = sum(abs(row[j]) for j in range(self.get_column_count()) if j != idx)
        return abs(row[idx]) >= row_sum

    def check_diagonal_dominance(self) -> bool:
        for i in range(self.get_row_count()):
            if not self.__check_row_diagonal_dominance(self.get_data()[i], i):
                return False
        return True

    def rearrange(self, results: Matrix = None) -> None:
        for i in range(self.get_row_count()):
            if not self.__check_row_diagonal_dominance(self.get_data()[i], i):
                for j in range(i + 1, self.get_row_count()):
                    if self.__check_row_diagonal_dominance(self.get_data()[j], i):
                        self.get_data()[i], self.get_data()[j] = self.get_data()[j], self.get_data()[i]
                        if results is not None:
                            results.get_data()[i], results.get_data()[j] = results.get_data()[j], results.get_data()[i]
                        break
                else:
                    raise ValueError("Diagonal dominance cannot be achieved")
                if i == self.get_row_count() - 1:
                    raise ValueError("Diagonal dominance cannot be achieved")
