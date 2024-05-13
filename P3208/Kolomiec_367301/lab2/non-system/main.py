import draw
import half_division
import nuton
import simple_iteration
import tools

equations = [[1, -3.125, -3.5, 2.458], [1, 0, -1, 4], [1, 2, 3, 4]]
select_method = [lambda eq, sec: nuton.calculate(eq, sec),
                 lambda eq, sec: half_division.calculate(eq, sec),
                 lambda eq, sec: simple_iteration.calculate(eq, sec)]
correct = "123"
methods = "1. Метод Ньютона\n2. Метод половинного деления\n3. Метод простой итерации\n"
finish = "----------------------------------SUCCESS----------------------------------"
run = True

while run:
    tools.print_all(equations)
    choose = input("Введите номер уравнения: ").strip()
    if choose not in correct:
        print("Не понял вас. Попробуйте еще!")
        continue
    choose = equations[int(choose) - 1]

    method_choose = input(f"{methods}Выберите метод: ").strip()
    if method_choose not in correct:
        print("Не понял вас. Попробуйте еще!")
        continue

    method_choose = int(method_choose) - 1

    sections = tools.get_sections(choose)
    print("\n".join([f"{i + 1}. {sections[i]}" for i in range(len(sections))]))
    sec_variant = input("Выберите диапазон для поиска решения: ").strip()
    if sec_variant not in correct:
        print("Не понял вас. Попробуйте еще!")
        continue

    sec_variant = int(sec_variant) - 1

    select_method[method_choose](choose, sections[sec_variant])
    draw.draw(choose)
    print(finish)
