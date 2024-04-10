from Rectangle import rectangle
from Trapezoid import trapezoid
from Simpson import simpson

equation = int(input("Выберите функцию:\n1. x^2\n2. -3x^3 - 5x^2 + 4x - 2\n3. -x^3 - x^2 + x + 3\n4. 1 / ln(x)\n> "))
lower_a = int(input("Введите нижний предел интегрирования: "))
upper_b = int(input("Введите верхний предел интегрирования: "))

method = int(
    input("Выберите метод решения интеграла:\n1. Метод прямоугольников\n2. Метод трапеций.\n3. Метод Симпсона.\n> "))
if method == 1:
    mode = int(input(
        "Выберите модификацию метода:\n1. Метод левых прямоугольников.\n2. Метод правых прямоугольников.\n3. Метод "
        "средних прямоугольников.\n> "))
    if mode == 1 or mode == 2:
        n = int(input("Введите число разбиения интервала интегрирования: "))
        if n < 4:
            n = 4
        res = rectangle(lower_a, upper_b, None, equation, mode, n)
        print(f"I = {res[0]}; count = {n}")
    elif mode == 3:
        e = float(input("Введите точность: "))
        res = rectangle(lower_a, upper_b, e, equation, mode, 4)
        print(f"I = {res[0]}; count = {res[1]}")
    else:
        raise ValueError("Неизвестный режим.")
elif method == 2:
    e = float(input("Введите точность: "))
    res = trapezoid(lower_a, upper_b, e, equation, 4)
    print(f"I = {res[0]}; count = {res[1]}")
elif method == 3:
    e = float(input("Введите точность: "))
    res = simpson(lower_a, upper_b, e, equation, 4)
    print(f"I = {res[0]}; count = {res[1]}")
else:
    raise ValueError("Неизвестный режим.")
