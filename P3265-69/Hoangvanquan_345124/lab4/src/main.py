import Approximation
from Graph import (
        getDataInput,
        getDataFile,
        output,
        show_graph
    )

FILE_OUT = 'C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\code\\codeC++\\python\\Math_Lab4\\test_out'

def InputMode():
    choice = input("Введите '+' или '-' для выбора способа ввода: ")
    while (choice != '+') and (choice != '-'):
        choice = input("Введите '+' или '-' для выбора способа ввода: ")
    return choice


if __name__ == '__main__':
    print("ЛАБОРАТОРНАЯ РАБОТА")
    print("--------------------------------------------")
    inputMode = InputMode()
    if(inputMode == '+'): read = (lambda: getDataFile())
    else: read = (lambda: getDataInput())
    x, y = read()

    Approximations = [
        ('Линейная функция', Approximation.Approximation_linear),
        ('Полиномиальная функция 2-й степени', Approximation.Approximation_degree2),
        ('Полиномиальная функция 3-й степени', Approximation.Approximation_degree3),
        ('Экспоненциальная функция', Approximation.Approximation_exp),
        ('Логарифмическая функция', Approximation.Approximation_logarith),
        ('Степенная функция', Approximation.Approximation_power),
    ]
    
    results = [a[1](x, y) for a in Approximations]
    index_min = min(range(len(results)), key=results.__getitem__)

    write_mode = input('Вывод в файл (+/-): ')
    file = open(FILE_OUT, 'w', encoding='utf-8') if (write_mode == '+') else None

    out = lambda x: output(x, file)

    for i, result in enumerate(results):
        name = Approximations[i][0]
        out(f'--- {name}')
        out(f'φ(x) = {result.function}')
        out(f'S = {result.S:.4f}')
        out(f'δ = {result.deviation:.4f}')
        out(f'R² = {result.confidence:.4f}')
        if result.r:
            out(f'r = {result.r:.4f}')

    out(f'Лучше всего аппроксимирует‚ {Approximations[index_min][0]}: '
        f'δ = {results[index_min].deviation:.3f}')

    show_graph(x, y, results)