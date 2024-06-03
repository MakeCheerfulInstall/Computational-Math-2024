from P3213.Markov_367380.lab4.dto.point import Point
from P3213.Markov_367380.lab4.dto.request import Request
from P3213.Markov_367380.lab4.dto.response import Response
from P3213.Markov_367380.lab4.approx.utils.all_aproximators import *
from P3213.Markov_367380.lab4.utils.utils_functions import show, out

MAX_POINTS: int = 12
MIN_POINTS: int = 8


def main():
    if input("Do you want to input with file? y/n: ") == "y":
        request: Request = file_input()
    else:
        request: Request = cli_input()

    min_sd: float = float("inf")
    min_type: str = ""
    for approximator in approximators:
        response: Response = approximator.approximate(request)
        out(response)
        if not response.status_code:
            show(response)
            if response.sd < min_sd:
                min_sd = response.sd
                min_type = response.type.value
    print(f'Наиболее точна {min_type.lower()}. СКО = {min_sd:.3g}')

def cli_input() -> Request:
    points: list[Point] = []

    while len(points) < MAX_POINTS:
        point_str: str = input("Введите координаты точки (x, y) через пробел: ")
        coordinates: list[str] = point_str.split()

        if len(coordinates) != 2:
            print("Пожалуйста, введите ДВЕ координаты через пробел.")
            continue

        try:
            x: float = float(coordinates[0])
            y: float = float(coordinates[1])
            points.append(Point(x, y))
        except ValueError:
            print("Пожалуйста, введите ЧИСЛОВЫЕ значения для координат x и y.")
            continue

        if len(points) >= MIN_POINTS:
            end_input: str = input("Хотите добавить еще точку? (y/n): ")
            if end_input.lower() != "y":
                break
    return Request(points)


def file_input() -> Request:
    file_path: str = input("Введите путь к файлу с координатами точек: ")
    points: list[Point] = []
    while True:
        try:
            with open(file_path, 'r') as file:
                for line in file:
                    coordinates = line.strip().split()

                    # проверяем, что в строке есть две координаты
                    if len(coordinates) != 2:
                        print(f"Ошибка в строке: {line}. Пожалуйста, убедитесь, что каждая строка содержит координаты.")
                        continue

                    try:
                        x = float(coordinates[0])
                        y = float(coordinates[1])
                        points.append(Point(x, y))
                    except ValueError:
                        print(
                            f"Ошибка в строке: {line}. Пожалуйста, убедитесь, что значения координат являются числами.")
                        continue
            if MIN_POINTS <= len(points) <= MAX_POINTS:
                return Request(points)
            else:
                print(f"Количество точек должно быть от {MIN_POINTS} до {MAX_POINTS}")

        except FileNotFoundError:
            print("Файл не найден. Пожалуйста, убедитесь, что указанный файл существует.")

main()

