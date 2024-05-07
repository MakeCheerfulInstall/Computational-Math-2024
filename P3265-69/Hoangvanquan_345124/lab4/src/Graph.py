import numpy as np
import matplotlib.pyplot as plt

FILE_IN = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\code\\codeC++\\python\\Math_Lab4\\test"

def getData(lines):
    x = list(map(float, lines[0].split()))
    y = list(map(float, lines[1].split()))
    return x, y

def getDataInput():
    print('Введите x, y в горизонтальных строках:')
    lines = [input('x: '), input('y: ')]
    return getData(lines)

def getDataFile():
    with open(FILE_IN, 'r') as file:
        lines = list(map(lambda x: x.rstrip('\n'), file.readlines()))
        return getData(lines)

def output(s, file=None):
    if file: 
        file.write(str(s) + '\n')
    else: 
        print(s)

OFFSET = 5

def show_graph(xs, ys, results):
    x1, x2, y1, y2 = min(xs), max(xs), min(ys), max(ys)
    bx, by = max(abs(x1), abs(x2)) + OFFSET, max(abs(y1), abs(y2)) + OFFSET
    x = np.linspace(min(xs) - OFFSET, max(xs) + OFFSET, 200)

    _figure = plt.figure()
    ax = _figure.add_subplot(1, 1, 1)

    plt.grid(True)
    plt.xlim((-bx, bx))
    plt.ylim((-by, by))

    ax.spines['left'].set_position('center')
    ax.spines['bottom'].set_position('center')
    ax.spines['right'].set_color('none')
    ax.spines['top'].set_color('none')

    for result in results:
        xt = x
        y = np.vectorize(result.apply)
        try:
            y(x)
        except ValueError:
            xt = x[x > 0]
        finally:
            ax.plot(xt, y(xt), label=result.function)

    ax.plot(xs, ys, 'ro')
    plt.legend()

    plt.show()