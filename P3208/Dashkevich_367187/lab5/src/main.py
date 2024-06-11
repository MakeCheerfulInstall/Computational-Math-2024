from methods.lagrange import *
from methods.newton import *
from methods.newton_stable import *
from methods.striling import *
from methods.bessel import *
import matplotlib.pyplot as plt

clc_x = 0
def_functions = [lambda x : x**3 - 4*x**2 + 1.23*x - 11, lambda x : math.sin(x)]
points = []
functions = []


def is_stable(points):
    delta = round(points[1][0] - points[0][0], 5)
    for i in range(len(points) - 1):
        if round(points[i + 1][0] - points[i][0], 5) != delta:
            return False
    return True


def show_plot():
    x_arr = [min(points, key=lambda x: x[0])[0] + (max(points, key=lambda x: x[0])[0] - min(points, key=lambda x: x[0])[0])/1000*i for i in range(1000)]
    x_arr[-1] = max(points, key=lambda x: x[0])[0]
    for f in functions:
        y_arr = [f.calc(x) for x in x_arr]
        plt.plot(x_arr, y_arr, label=f.getName())

    for p in points:
        plt.scatter(p[0], p[1])

    plt.grid(True)
    plt.axhline(0, color='black', linewidth=1)
    plt.axvline(0, color='black', linewidth=1)

    plt.legend()
    plt.show()


filename = input("Enter file name or press Enter for manual input: ")
if filename == '':
    n = int(input("Enter number of points or type 0 for accessing the predetermined functions: "))
    if n != 0:
        for _ in range(n):
            points.append(list(map(float, input().split())))
    else:
        n = int(input("Choose your function:\n"
                      "1. x^3 - 4x^2 + 1.23*x - 11\n"
                      "2. sin(x)\n"))
        n -= 1
        a, b = map(float, input("Enter interval end points: ").strip().split())
        h = int(input("Enter amount of points: "))
        points = [[a + (b-a)/h*i, def_functions[n](a + (b-a)/h*i)] for i in range(h)]
        points.append([b, def_functions[n](b)])
else:
    with open(f"./resources/{filename}", 'r') as file:
        for line in file:
            points.append(list(map(float, line.strip().split())))
    print("File read successful")

points = sorted(points, key=lambda x: x[0])
print(points)

functions.append(Lagrange_Polynomial(points))

functions.append(Newton_Polynomial(points))
x_check = float(input("Enter X point for calculation: "))

for f in functions:
    print()
    print(f.getName())
    print("function:", f.tostr())
    print("f(", x_check, ") =", f.calc(x_check))

functions[-1].print_tree()
show_plot()

if is_stable(points):
    print("\nnewton_stable")
    newton_stable = Newton_Stable_Polynomial(points)
    newton_stable.print_tree()

    if x_check <= (points[-1][0] - points[0][0]) / 2:
        print("front: ", newton_stable.calc_straight(x_check))
    else:
        print("back: ", newton_stable.calc_back(x_check))
    if len(points) % 2 == 1:
        stir = Stirling_polynom(points)
        print(f"Stirling: {stir.calc(x_check)}")
    else:
        bess = Bessel_polynom(points)
        print(f"Bessel: {bess.calc(x_check)}")
else:
    print("distance between point isn't stable")
