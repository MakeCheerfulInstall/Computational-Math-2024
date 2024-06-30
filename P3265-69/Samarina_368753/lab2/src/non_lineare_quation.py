from data_imput import *
from validate_input import check_convergencecondition, converted_quation, quation_df2_solution, quation_df_solution, quation_solution

def str_quation(quation):
    if quation == 1:
        return "x^2 - 3x + 2"
    elif quation == 2:
        return "x^3 + 2x^2 - 5"
    elif quation == 3:
        return "cos(x) + x^2"

def validate_initial_approximation(quation,a,b):
    fa = quation_solution(quation,a)
    fb =  quation_solution(quation,b)
    dfa2 =quation_df2_solution(quation,a)
    dfb2 = quation_df2_solution(quation,b)

    if fa * dfa2 > 0:
        return a
    elif fb * dfb2 > 0:
        return b
    else:
        raise ValueError("No suitable initial approximation found on the interval [a, b]")

def Chord_method(quation, method):
    a, b, inaccuracy = input_selection(quation,method)
    a1 =a
    b1 = b
    iterations = 0
    max_iter = 10000
    x0 = 99999
    while abs(quation_solution(quation, x0)) > inaccuracy and iterations < max_iter:
        x0 = a1 - ((b1-a1)/(quation_solution(quation,b1)-quation_solution(quation,a1)))*quation_solution(quation,a1)
        if quation_solution(quation, x0) * quation_solution(quation, a1) < 0:
            b1 = x0
        else:
            a1 = x0
        iterations += 1

    solution = try_to_convert_to_int(x0) 

    output_data(solution, quation_solution(quation, solution), iterations, str_quation(quation))
    draw_grapth(quation, str_quation(quation), a1, b1)

def Newton_method(quation, method):
    a, b, inaccuracy = input_selection(quation,method)

    approximation = validate_initial_approximation(quation, a, b)

    iterations = 0
    max_iter = 1000
    x = approximation
    while abs(quation_solution(quation,x)) > inaccuracy and iterations < max_iter:
        x = x - quation_solution(quation,x) / quation_df_solution(quation,x)
        iterations += 1

    solution = try_to_convert_to_int(x)
    output_data(solution, quation_solution(quation, solution), iterations, str_quation(quation))
    draw_grapth(quation, str_quation(quation), a, b)

def Simple_iteration_method(quation, method):
    a, b, inaccuary = input_selection(quation,method)
    # lambda_L = -1/(max(abs(quation_df_solution(quation,a)), abs(quation_df_solution(quation,b))))
    # print("L = ",lambda_L)
    q = check_convergencecondition(quation, a, b)
    approximation = validate_initial_approximation(quation, a, b)
    x = approximation
    iterations = 0
    max_iter = 1000
    if q>1:
        print("The convergence condition is NOT met")
        exit()
    elif( 0< q <= 0.5):
        while abs(converted_quation(quation,x)-x) > inaccuary and iterations < max_iter:
            x = converted_quation(quation,x)
            print(x)
            iterations += 1
    elif(0.5 <q <1):
        while abs(converted_quation(quation,x)-x) > ((1-q)/q)*inaccuary and iterations < max_iter:
            x = converted_quation(quation,x)
            iterations += 1







    solution = try_to_convert_to_int(x)
    output_data(solution, quation_solution(quation, solution), iterations, str_quation(quation))
    draw_grapth(quation, str_quation(quation), a, b)


def non_lineare_quation():
    quation = choose_quation()
    method = choose_method()


    switch_command = {
        1: Chord_method,
        2: Newton_method,
        3: Simple_iteration_method,
        4: exit,
    }
    switch_command.get(method, exit)(quation,method)