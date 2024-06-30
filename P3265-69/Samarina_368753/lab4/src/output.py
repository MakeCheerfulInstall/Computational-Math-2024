import os
import matplotlib.pyplot as plt
import numpy as np


def output_data(
    pairs,
    answers,
    linear_answer,
    sqr_answer,
    cubic_answer,
    log_answer,
    exp_answer,
    gradual_answer,
):
    input_variant = choose_output_format()
    switch_command = {
        1: output_in_console,
        2: output_in_file,
    }
    switch_command.get(input_variant, exit)(
        answers,
        linear_answer,
        sqr_answer,
        cubic_answer,
        log_answer,
        exp_answer,
        gradual_answer,
    )
    draw_grapth(
        pairs,
        answers,
        linear_answer,
        sqr_answer,
        cubic_answer,
        log_answer,
        exp_answer,
        gradual_answer,
    )


def choose_output_format():
    while True:
        try:
            print("Choose the output format:")
            input_variant = int(input("1) In console\n2) In file\n"))
            if input_variant in range(1, 3):
                break
            print("Invalid command")
            continue
        except ValueError:
            print("Incorrect value entered")
            continue
    return input_variant


def output_in_console(
    answers,
    linear_answer,
    sqr_answer,
    cubic_answer,
    log_answer,
    exp_answer,
    gradual_answer,
):
    print(find_best_method(answers))

    try:
        print(print_linear(linear_answer))
    except TypeError:
        print()
    try:
        print(print_sqr(sqr_answer))
    except TypeError:
        print()
    try:
        print(print_qubic(cubic_answer))
    except TypeError:
        print()
    try:
        print(print_log(log_answer))
    except TypeError:
        print()
    try:
        print(print_exp(exp_answer))
    except TypeError:
        print()
    try:
        print(print_gradual(gradual_answer))
    except TypeError:
        print()


def output_in_file(
    answers,
    linear_answer,
    sqr_answer,
    cubic_answer,
    log_answer,
    exp_answer,
    gradual_answer,
):
    try:
        filename = input("Enter the file name to save the data: ")

        file_path = os.path.join("./lb4/solutions", filename)

        if not os.path.exists("./lb4/solutions"):
            os.makedirs("./lb4/solutions")
        otput_best = find_best_method(answers)
        with open(file_path, "w") as file:
            file.write(otput_best)

            try:
                file.write(print_linear(linear_answer))
            except TypeError:
                file.write("\n")
            try:
                file.write(print_sqr(sqr_answer))
            except TypeError:
                file.write("\n")
            try:
                file.write(print_qubic(cubic_answer))
            except TypeError:
                file.write("\n")
            try:
                file.write(print_log(log_answer))
            except TypeError:
                file.write("\n")
            try:
                file.write(print_exp(exp_answer))
            except TypeError:
                file.write("\n")
            try:
                file.write(print_gradual(gradual_answer))
            except TypeError:
                file.write("\n")

        print(
            f"The values of variables have been successfully written to the file: {file_path}"
        )
    except FileNotFoundError:
        print("The specified path does not exist.")
    except PermissionError:
        print("You do not have permission to create a file in this directory.")


def print_linear(answers):
    fi = "fi ="
    ei = "ei ="
    for num in answers[3]:
        fi += f" {num},"
    for num in answers[4]:
        ei += f" {num},"
    pi = "Pi ="
    for num in answers[6]:
        pi += f" {num},"
    pirson = answers[5]
    return f"Linear\n{answers[2]}\n{fi}\nPirson = {pirson}\nS = {answers[1]}\nS2 = {answers[0]}\n{ei}\n{pi}\n\n"


def print_sqr(answers):
    fi = "fi ="
    ei = "ei ="
    for num in answers[3]:
        fi += f" {num},"
    for num in answers[4]:
        ei += f" {num},"
    pi = "Pi ="
    for num in answers[5]:
        pi += f" {num},"
    return f"Quadratic\n{answers[2]}\n{fi}\nS = {answers[1]}\nS2 = {answers[0]}\n{ei}\n{pi}\n\n"


def print_qubic(answers):
    fi = "fi ="
    ei = "ei ="
    for num in answers[3]:
        fi += f" {num},"
    for num in answers[4]:
        ei += f" {num},"
    return f"Qubic\n{answers[2]}\n{fi}\nS = {answers[1]}\nS2 = {answers[0]}\n{ei}\n\n"


def print_log(answers):
    fi = "fi ="
    ei = "ei ="
    for num in answers[3]:
        fi += f" {num},"
    for num in answers[4]:
        ei += f" {num},"
    pi = "Pi ="
    for num in answers[6]:
        pi += f" {num},"
    pirson = answers[5]
    return f"Logarithmic\n{answers[2]}\n{fi}\nPirson = {pirson}\nS = {answers[1]}\nS2 = {answers[0]}\n{ei}\n{pi}\n\n"


def print_exp(answers):
    fi = "fi ="
    ei = "ei ="
    for num in answers[3]:
        fi += f" {num},"
    for num in answers[4]:
        ei += f" {num},"
    pi = "Pi ="
    for num in answers[6]:
        pi += f" {num},"
    pirson = answers[5]
    return f"Exp\n{answers[2]}\n{fi}\nPirson = {pirson}\nS = {answers[1]}\nS2 = {answers[0]}\n{ei}\n{pi}\n\n"


def print_gradual(answers):
    fi = "fi ="
    ei = "ei ="
    for num in answers[3]:
        fi += f" {num},"
    for num in answers[4]:
        ei += f" {num},"
    pi = "Pi ="
    for num in answers[6]:
        pi += f" {num},"
    pirson = answers[5]
    return f"Gradual\n{answers[2]}\n{fi}\nPirson = {pirson}\nS = {answers[1]}\nS2 = {answers[0]}\n{ei}\n{pi}\n\n"


def find_best_method(answers):
    min = 99999

    for answer in answers:
        try:
            if answer[0] < min:
                min = answer[0]
            # print(answer[2], end="zz")
        except TypeError:
            continue
    for answer in answers:
        try:
            if answer[0] == min:
                return f"Best quation is {answer[2]} with S2 = {answer[0]}\n"
        except TypeError:
            continue


def draw_grapth(
    pairs,
    answers,
    linear_answer,
    sqr_answer,
    cubic_answer,
    log_answer,
    exp_answer,
    gradual_answer,
):
    min = 99999
    max = -99999
    x_values_dots = [pair[0] for pair in pairs]
    y_values_dots = [pair[1] for pair in pairs]
    for pair in pairs:
        if pair[0] <= min:
            min = pair[0]
        if pair[0] >= max:
            max = pair[0]
    x = np.linspace(min - min * 0.3, max + max * 0.3, 400)
    try:
        y_values_linear = linear_answer[7] * x + linear_answer[8]
        plt.plot(x, y_values_linear, label=linear_answer[2], color="#461d9f")
    except TypeError:
        pass

    try:
        y_values_sqr = sqr_answer[6] * x**2 + sqr_answer[7] * x + sqr_answer[8]
        plt.plot(x, y_values_sqr, label=sqr_answer[2], color="#FF5733")
    except TypeError:
        pass

    try:
        y_values_cubic = (
            cubic_answer[5] * x**3
            + cubic_answer[6] * x**2
            + cubic_answer[7] * x
            + cubic_answer[8]
        )
        plt.plot(x, y_values_cubic, label=cubic_answer[2], color="#6C5CE7")
    except TypeError:
        pass

    try:
        y_values_log = log_answer[7] * np.log(x) + log_answer[8]
        plt.plot(x, y_values_log, label=log_answer[2], color="#F08A5B")
    except TypeError:
        pass

    try:
        y_values_exp = exp_answer[7] * x + np.log(exp_answer[8])
        plt.plot(x, y_values_exp, label=exp_answer[2], color="#5F27CD")
    except TypeError:
        pass

    try:
        y_values_grad = gradual_answer[7] * np.log(x) + np.log(gradual_answer[8])
        plt.plot(x, y_values_grad, label=gradual_answer[2], color="000")
    except TypeError:
        pass

    plt.scatter(x_values_dots, y_values_dots, label="Точки", color="red", linewidths=2)
    # Добавим заголовок и метки осей
    plt.title("График функций")
    plt.xlabel("x")
    plt.ylabel("f(x)")

    # Добавим сетку
    plt.grid(True)

    # Отобразим линии вспомогательных осей
    plt.axhline(0, color="black", linewidth=0.5)
    plt.axvline(0, color="black", linewidth=0.5)

    # Добавим легенду
    plt.legend()

    # Отобразим график
    plt.show()
