from __future__ import annotations
from typing import List
import random
from utils import to_float


class Vector:
    elems: List[float] = None

    def __init__(self, elems_or_size: int | List[float]) -> None:
        if isinstance(elems_or_size, List):
            self.elems = elems_or_size
        else:
            self.elems = [0 for _ in range(elems_or_size)]

    def size(self):
        return len(self.elems)

    def copy(self) -> Vector:
        return Vector(self.elems[:])

    def __getitem__(self, index: int | slice) -> float | Vector:
        if isinstance(index, slice):
            return Vector(self.elems[index])
        else:
            return self.elems[index]

    def __setitem__(self, key: int, value: float) -> None:
        self.elems[key] = value

    def swap_elems(self, index1: int, index2: int) -> None:
        self[index1], self[index2] = self[index2], self[index1]

    def has_only_zeros(self) -> bool:
        return max(self.elems) == 0 and min(self.elems) == 0

    def __mul__(self, num_or_vector: float | Vector) -> Vector:
        new_vec: Vector = Vector(self.size())

        for i in range(self.size()):
            if isinstance(num_or_vector, float):
                new_vec[i] = self[i] * num_or_vector
            else:
                new_vec[i] = self[i] * num_or_vector[i]

        return new_vec

    def sum(self) -> float:
        return sum(self.elems)

    def __add__(self, other) -> Vector:
        new_vec: List[float] = [0 for _ in range(self.size())]
        for i in range(self.size()):
            new_vec[i] = self[i] + other[i]

        return Vector(new_vec)


class Matrix:
    elems: List[Vector] = None

    def __init__(self, elems_or_size: List[Vector] | int) -> None:
        if isinstance(elems_or_size, int):
            self.elems = [Vector(elems_or_size) for _ in range(elems_or_size)]
        else:
            self.elems = elems_or_size

    def size(self):
        return len(self.elems)

    def __getitem__(self, index: int) -> Vector:
        return self.elems[index]

    def __setitem__(self, key: int, row: Vector) -> None:
        self.elems[key] = row

    def copy(self) -> Matrix:
        return Matrix([row.copy() for row in self.elems])

    def swap_rows(self, index1: int, index2: int) -> None:
        self[index1], self[index2] = self[index2], self[index1]

    def is_correct(self) -> bool:
        for row in self.elems:
            if row.has_only_zeros():
                return False

        for i in range(self.size()):
            col_has_non_zero: bool = False
            for j in range(self.size()):
                if self[j][i] != 0:
                    col_has_non_zero = True

            if not col_has_non_zero:
                return False

        return True


class Equation:
    matrix: Matrix = None
    b_vector: Vector = None
    det: float = None
    triangled: Equation = None
    answers: Vector = None
    residuals: Vector = None

    def __init__(self, matrix: Matrix, b_vector: Vector) -> None:
        self.matrix = matrix
        self.b_vector = b_vector

    @staticmethod
    def make_random(size: int) -> Equation:
        matrix: Matrix = Matrix(size)
        b_vector: Vector = Vector(size)
        for i in range(size):
            for j in range(size + 1):
                num: float = round(random.uniform(-10, 10), 2)
                if j == size:
                    b_vector[i] = num
                else:
                    matrix[i][j] = num

        return Equation(matrix, b_vector)

    @staticmethod
    def parse_file(filepath: str) -> Equation | None:
        try:
            with open(filepath, 'r') as f:
                lines: List[str] = f.readlines()
        except (FileNotFoundError, IsADirectoryError):
            print("No such file")
            return None

        size: int = len(lines)
        matrix: Matrix = Matrix(size)
        b_vector: Vector = Vector(size)
        try:
            for i in range(size):
                row: Vector = Vector(list(map(to_float, lines[i].split())))
                if row.size() != size + 1:
                    raise ValueError

                matrix[i] = row[:-1]
                b_vector[i] = row[-1]
        except ValueError:
            print("Make sure you have right matrix in the file")
            return None

        return Equation(matrix, b_vector)

    @staticmethod
    def parse_commandline(size: int) -> Equation | None:
        matrix: Matrix = Matrix(size)
        b_vector: Vector = Vector(size)
        print("Enter line by line 'a1 a2 ... an b' separated by a space:")
        try:
            for i in range(size):
                row: Vector = Vector(list(map(to_float, input().split())))
                if row.size() != size + 1:
                    raise ValueError

                matrix[i] = row[:-1]
                b_vector[i] = row[-1]
        except ValueError:
            print("I'm a teapot")
            return None

        return Equation(matrix, b_vector)

    def copy(self) -> Equation:
        return Equation(self.matrix.copy(), self.b_vector.copy())

    def size(self) -> int:
        return self.b_vector.size()

    def __str__(self) -> str:
        output: str = ''
        for i in range(self.size()):
            for j in range(self.size() + 1):
                if j == self.size():
                    output += f'=\t{self.b_vector[i]:.5g}\n'
                else:
                    output += f'{self.matrix[i][j]:.5g}\t'

        return output

    # Приведение к треугольному виду
    def triangle(self) -> None:
        perm_count = 0
        triangled_eq: Equation = self.copy()
        for i in range(triangled_eq.size() - 1):
            # Выбор главного элемента
            max_row_idx: int = i
            for j in range(i, triangled_eq.size()):
                if abs(triangled_eq.matrix[j][i]) > abs(triangled_eq.matrix[max_row_idx][i]):
                    max_row_idx = j

            # Перестановка строк
            if max_row_idx != i:
                triangled_eq.matrix.swap_rows(i, max_row_idx)
                triangled_eq.b_vector.swap_elems(i, max_row_idx)
                perm_count += 1

            # Убираем x[i][i] из каждой строки ниже i
            for j in range(i + 1, triangled_eq.size()):
                kf: float = - triangled_eq.matrix[j][i] / triangled_eq.matrix[i][i]

                adding_vec = triangled_eq.matrix[i] * kf
                triangled_eq.matrix[j] += adding_vec

                adding_b = kf * triangled_eq.b_vector[i]
                triangled_eq.b_vector[j] += adding_b

        self.triangled: Equation = triangled_eq
        self.set_det(perm_count)

    # Считает детерминант треугольной матрицы
    def set_det(self, k: int) -> None:
        self.det: float = 1
        for i in range(self.size()):
            self.det *= self.triangled.matrix[i][i]

        self.det *= ((-1) ** k)

    # Решает уравнение, считает вектор ответов
    def calc_answers(self) -> None:
        if not self.matrix.is_correct():
            return

        self.triangle()

        if self.det == 0:
            return

        answers: Vector = Vector(self.size())
        for i in range(self.size() - 1, -1, -1):
            answer = self.triangled.b_vector[i]
            for j in range(self.size() - 1, i, -1):
                answer -= self.triangled.matrix[i][j] * answers[j]
            answers[i] = answer / self.triangled.matrix[i][i]

        self.answers = answers

    # Считает вектор невязок
    def calc_residuals(self) -> None:
        residuals: Vector = Vector(self.size())

        if self.answers is None:
            return

        for i in range(self.size()):
            residuals[i] = (self.matrix[i] * self.answers).sum() - self.b_vector[i]

        self.residuals = residuals

    def print_answers(self) -> None:
        if self.answers is None:
            return

        print('Answers:')
        for i in range(self.size()):
            print(f'\tx{i + 1} = {self.answers[i]:.5g}')

    def print_residuals(self) -> None:
        if self.residuals is None:
            return

        print('Residuals:')
        for i in range(self.size()):
            print(f'\tr{i + 1} = {self.residuals[i]:.5g}')

    def solve(self) -> None:
        self.calc_answers()

        if self.answers is None:
            print('Invalid matrix! No answers or any answer')
            return

        self.calc_residuals()

        print(f'Determinant = {self.det:g}')
        print()
        print(f'Triangled equation:\n{self.triangled}')
        self.print_answers()
        print()
        self.print_residuals()
