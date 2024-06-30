import os
from validate_input import count_roots_on_interval, quation_solution, validate_roots
import numpy as np
import matplotlib.pyplot as plt


def try_to_convert_to_int(number):
    try:
        number_float = float(number)
        if number_float.is_integer():
            return int(number_float)
        else:
            return number_float
    except ValueError:  
        return float(number)


def hand_input(quation, method):
    while True:    
        a = choose_left_interval()
        b = choose_right_interval(a)
        count_roots = count_roots_on_interval(quation, a, b, 0.001) 
        if not (validate_roots(count_roots)):
            continue
        break
    inaccuracy = choose_inaccuracy()
    return a, b, inaccuracy

def file_input(quation, method):
    current_working_directory = os.path.dirname(__file__)
    file_name = input("Enter the relative path to your file\n")
    print()
    file_path = os.path.join(current_working_directory, file_name)
    with open(file_path, "r", encoding="utf-8") as file:
    # Читаем строки из файла
        lines = file.readlines()


    if len(lines) == 3:
        data =[]
        for line in lines:
            try:
                var = line.replace(",", ".")
                var = float(var)
                var = try_to_convert_to_int(var)
                data.append(var)
            except ValueError:
                print("Incorrect data in the specified file")
                exit()

            except UnboundLocalError:
                print("Incorrect data in the specified file")
                exit()
            continue

        a = data[0]
        b = data[1]
        inaccuracy = data[2]
        print("Readed a = ", a, "b = ", b, "inaccuracy = ", inaccuracy)
        count_roots = count_roots_on_interval(quation, a, b, 0.001) 
        if not (validate_roots(count_roots)):
            exit()
    else:
        print("Uncorrect count of lines in the specified file")
        exit()
    return a, b, inaccuracy

def choose_inaccuracy():
    while True:
        try:
            inaccuracy= (
            input(
                "Enter the inaccuracy: "
            ).replace(",", ".")
            )
            inaccuracy = float(inaccuracy)
            return try_to_convert_to_int(inaccuracy)
        except ValueError:
            print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue

def input_selection(quation,method):
    while True:
        try:
            print("Choose the quation:")
            input_selection = int(
                input(
                    "1) Hand input \n2) File input\n"
                )
            )
            if input_selection in range(1,3):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    switch_command = {
        1: hand_input,
        2: file_input,
    }
    a, b, inaccuracy = switch_command.get(input_selection, exit)(quation, method)
    return a, b, inaccuracy

def choose_quation():
    while True:
        try:
            print("Choose the quation:")
            input_quation = int(
                input(
                    "1) x^2 - 3x + 2 \n2) x^3 + 2x^2 - 5\n3) sin(x) - cos(x)\n"
                )
            )
            if input_quation in range(1,4):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_quation

def choose_system():
    while True:
        try:
            print("Choose the quation:")
            input_quation = int(
                input(
                    "1) 0.1x^2 +x + 0.2y^2 - 0.3\n   0.2x^2 + y + 0.1xy -0.7\n2) 3y^2+0.5x^2-x\n   sin(x)^2-y\n"
                )
            )
            if input_quation in range(1,3):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_quation

def choose_method():
    while True:
        try:
            print("Choose the method:")
            input_variant = int(
                input(
                    "1) Chord method\n2) Newton's method\n3) Simple iteration method\n"
                )
            )
            if input_variant in range(1, 4):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_variant

def choose_system_method():
    while True:
        try:
            print("Choose the method:")
            input_variant = int(
                input(
                    "1) Simple iteration method\n"
                )
            )
            if input_variant in range(1, 2):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_variant

def choose_left_interval():
    while True:
        try:
            interval= (
            input(
                "Enter the left boundary of the interval: "
            ).replace(",", ".")
            )
            interval = int(interval)
            return try_to_convert_to_int(interval)

        except ValueError:
            try:
                interval =try_to_convert_to_int(interval)
                return interval
            except ValueError:
                print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_right_interval(a):
    while True:
        try:
            interval= (
            input(
                "Enter the right boundary of the interval: "
            ).replace(",", ".")
            )
            if(float(interval) - float(a) - 0.00001 < 0):
                print("The right boundary must be greater than the left boundary!")
                continue
            interval = int(interval)
            return try_to_convert_to_int(interval)

        except ValueError:
            try:
                interval =try_to_convert_to_int(interval)
                return interval
            except ValueError:
                print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue

def choose_x():
    while True:
        try:
            interval= (
            input(
                "Enter the x value: "
            ).replace(",", ".")
            )
            interval = int(interval)
            return try_to_convert_to_int(interval)

        except ValueError:
            try:
                interval =try_to_convert_to_int(interval)
                return interval
            except ValueError:
                print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue


def choose_y():
    while True:
        try:
            interval= (
            input(
                "Enter the y value: "
            ).replace(",", ".")
            )
            interval = int(interval)
            return try_to_convert_to_int(interval)

        except ValueError:
            try:
                interval =try_to_convert_to_int(interval)
                return interval
            except ValueError:
                print("Incorrect number entered")
            continue

        except UnboundLocalError:
            print("Incorrect number entered")
            continue

def choose_output_format():
    while True:
        try:
            print("Choose the output format:")
            input_variant = int(
                input(
                    "1) In console\n2) In file\n"
                )
            )
            if input_variant in range(1, 3):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_variant

def output_data(solution, function, iterations, quation):
    input_variant = choose_output_format()
    switch_command = {
        1: output_in_console,
        2: output_in_file,
    }
    switch_command.get(input_variant, exit)(solution, function, iterations, quation)
    

def output_in_console(solution, function, iterations, quation):
    print("Quation: ", quation, "Solution: ", solution, "f(", solution, ") = ", function, "iterations = ", iterations)

def output_in_file(solution, function, iterations, quation):
    try:
        filename = input("Enter the file name to save the data: ")

        file_path = os.path.join('./lb2/solutions', filename)

        if not os.path.exists('./lb2/solutions'):
            os.makedirs('./lb2/solutions')

        with open(file_path, 'w') as file:
            file.write("Quation: " + str(quation) + '\n')
            file.write("Solution: " + str(solution) + '\n')
            file.write("f("+ str(solution)+ ") = " + str(function) + '\n')
            file.write("iterations = " + str(iterations) + '\n')

        print(f"The values of variables have been successfully written to the file: {file_path}")

    except FileNotFoundError:
        print("The specified path does not exist.")
    except PermissionError:
        print("You do not have permission to create a file in this directory.")
def draw_grapth(quation,function, a, b):
    x_values = np.linspace(-a-a*0.3, b+b*0.3, 100)
    y_values = quation_solution(quation, x_values)

    # Построим график функции
    plt.plot(x_values, y_values, label=function, color='b')

    # Добавим заголовок и метки осей
    plt.title('График функции ' + function)
    plt.xlabel('x')
    plt.ylabel('f(x)')

    # Добавим сетку
    plt.grid(True)

    # Отобразим линии вспомогательных осей
    plt.axhline(0, color='black', linewidth=0.5)
    plt.axvline(0, color='black', linewidth=0.5)

    # Добавим легенду
    plt.legend()

    # Отобразим график
    plt.show()
