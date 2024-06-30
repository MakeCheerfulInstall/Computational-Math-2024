import sys
import numpy as np
import matplotlib.pyplot as plt
from data_imput import choose_inaccuracy,choose_x,choose_y,choose_system,choose_system_method
def non_lineare_system():
    system_num = choose_system()
    method = choose_system_method()
    

    switch_command = {
        1: simple_iteration_method,
        2: exit,
    }
    switch_command.get(method, exit)(system_num,method)
    
def simple_iteration_method(system,method) :
    x0 = choose_x()
    y0 = choose_y()
    a = x0
    b = y0
    inaccuracy = choose_inaccuracy()
    max_iter = 1000
    iteration =0
    
    if not check_convergence(system, method, x0, y0):
        print("The iteration matrix does not\nsatisfy the convergence condition.")
        exit()
    
    for i in range(max_iter):
        x = f1(x0, y0,system)
        y = f2(x0, y0,system)
        if (abs(x-x0)< inaccuracy)and(abs(y-y0)< inaccuracy):
            print(f"\n\nx = {x}\ny = {y}")
            print(f"Iterations = {iteration}")
            print(f"inncaury x = {x-f1(x,y,system)}\ninncaury y = {y-f2(x,y,system)}")
            break
        x0, y0 = x, y
        iteration += 1
        
        
    plt.figure(figsize=(12, 6))    
    
    x_values = np.linspace(-5, 5, 1000)
    y1_pos = np.array([f1_y_positive(x, system) for x in x_values])
    y1_neg = np.array([f1_y_negative(x, system) for x in x_values])
    y2_pos = np.array([f2_y_positive(x, system) for x in x_values])
    y2_neg = np.array([f2_y_negative(x, system) for x in x_values])
    plt.subplot(1, 2, 1)
    plt.plot(x_values, y1_pos, label=f'{str_quation_1(system)} (+)', color='r')
    plt.plot(x_values, y1_neg, label=f'{str_quation_1(system)} (-)',  color='r')
    plt.plot(x_values, y2_pos, label=f'{str_quation_2(system)} (+)', color='b')
    plt.plot(x_values, y2_neg, label=f'{str_quation_2(system)} (-)', color='b')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.axhline(0, color='black', linewidth=1.5)
    plt.axvline(0, color='black', linewidth=1.5)
    plt.title(f'System {system}')
    plt.grid(True)
    plt.legend()  
    
    plt.tight_layout()
    plt.show()
    
def f1(x,y,system):
    if(system == 1):
        return 0.3 - 0.1*x*x- 0.2*y*y
    elif(system == 2):
        return 3*y*y + 0.5*x*x
    else: 
        print("System out of choice")
        exit()
def f2(x,y,system):
    if(system == 1):
        return 0.7 -0.2*x*x - 0.1*x*y
    elif(system == 2):
        return np.sin(x)**2
    else:
        print("System out of choice")
        exit()
def check_convergence(system, method, x0, y0):
    jacobian_matrix = np.array([[f1_dx(system, x0, y0), f1_dy(system, x0, y0)], 
                                [f2_dx(system, x0, y0), f2_dy(system, x0, y0)]])
    eigenvalues = np.linalg.eigvals(jacobian_matrix)
    if np.all(np.abs(eigenvalues) < 1):
        return True
    else:
        return False

def f1_dx(system, x, y):
    if system == 1:
        return -0.2 * x
    elif system == 2:
        return x
    else:
        print("System out of choice")
        exit()

def f1_dy(system, x, y):
    if system == 1:
        return -0.4 * y
    elif system == 2:
        return 6*y
    else:
        print("System out of choice")
        exit()

def f2_dx(system, x, y):
    if system == 1:
        return -0.4 * x - 0.1 * y
    elif system == 2:
        return np.sin(2*x)
    else:
        print("System out of choice")
        exit()

def f2_dy(system, x, y):
    if system == 1:
        return 0
    elif system == 2:
        return 0
    else:
        print("System out of choice")
        exit()
def f1_y_positive(x, system):
    if system == 1:
        argument = (0.3 - x - 0.1 * x * x) / 0.2
        return np.sqrt(argument) if argument >= 0 else np.nan
    elif system == 2:
        argument = (x - 0.5 * x * x) / 3
        return np.sqrt(argument) if argument >= 0 else np.nan
    else:
        print("System out of choice")
        exit()

def f1_y_negative(x, system):
    if system == 1:
        argument = (0.3 - x - 0.1 * x * x) / 0.2
        return -np.sqrt(argument) if argument >= 0 else np.nan
    elif system == 2:
        argument = (x - 0.5 * x * x) / 3
        return -np.sqrt(argument) if argument >= 0 else np.nan
    else:
        print("System out of choice")
        exit()

def f2_y_positive(x, system):
    if system == 1:
        argument = (0.7 - 0.2 * x * x) / (1 + 0.1 * x)
        return argument
    elif system == 2:
        return np.abs(np.sin(x))
    else:
        print("System out of choice")
        exit()

def f2_y_negative(x, system):
    if system == 1:
        argument = (0.7 - 0.2 * x * x) / (1 + 0.1 * x)
        return argument
    elif system == 2:
        return np.abs(np.sin(x))
    else:
        print("System out of choice")
        exit()
def str_quation_1(system):
    if system == 1:
        return "0.1x^2 +x + 0.2y^2 - 0.3"
    elif system == 2:
        return "3y^2+0.5x^2-x"
def str_quation_2(system):
    if system == 1:
        return "0.2x^2 + y + 0.1xy -0.7"
    elif system == 2:
        return "sin(x)^2-y"
