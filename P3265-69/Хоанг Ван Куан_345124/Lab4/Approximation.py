from dataclasses import dataclass
import math
import numpy as np


@dataclass
class Result: 
    coefficients: iter
    apply: callable
    function: str
    S: float
    deviation: float 
    confidence: float
    r: float | None = None
    def __lt__(self, other):
        return self.deviation < other.deviation
    def __le__(self, other):
        return self.deviation == other.deviation

def getData(x, y):
    x = np.array(x)
    y = np.array(y)
    return x, y

# среднеквадратичное отклонение
def msr(phi, y):
    return (np.sum((phi - y) ** 2) / len(y)) ** 0.5

#  достоверность аппроксимации
def confidence(phi, y):
    return 1 - np.sum((y - phi) ** 2) / (np.sum(phi ** 2) - np.sum(phi) ** 2 / len(y))

# Коэффициент корреляции
def r(x, y):
    x0, y0 = np.mean(x), np.mean(y)
    return np.sum((x - x0) * (y - y0)) / (np.sum((x - x0) ** 2) * np.sum((y - y0) ** 2)) ** 0.5

# линейная функция - done
def Approximation_linear(x, y):
    x, y = getData(x, y)
    SX, SXX, SY, SXY = np.sum(x), np.sum(x ** 2), np.sum(y), np.sum(x * y)
    b, a = np.linalg.solve(
        np.array([[len(x), SX], [SX, SXX]]),
        np.array([SY, SXY])
    )
    # Сумма квадратов отклонений 
    phi = a * x + b
    S = np.sum((phi - y) ** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a * x + b,
        function = f'{a:.4f}x + {b:.4f}',
        S = S,
        r = r(x, y),
        confidence = confidence(phi, y),
        deviation = msr(phi, y)
    )

# полиномиальная функция 2-й степени - done
def Approximation_degree2(x, y):
    x, y = getData(x, y)
    SX, SXX, SXXX, SXXXX, SY, SXY, SXXY = np.sum(x), np.sum(x**2), np.sum(x**3), np.sum(x**4), np.sum(y), np.sum(x*y), np.sum(x*x*y)
    a0, a1, a2 = np.linalg.solve(
        np.array([[len(x), SX, SXX], [SX, SXX, SXXX], [SXX, SXXX, SXXXX]]),
        np.array([SY, SXY, SXXY])
    ) 

     # Сумма квадратов отклонений 
    phi = a2*x**2 + a1*x + a0
    S = np.sum((phi - y) ** 2)

    return Result(
        coefficients = (a0, a1, a2),
        apply = lambda x: a2*x**2 + a1*x + a0,
        function = f'{a2:.4f}x² + {a1:.4f}x + {a0:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )    

# полиномиальная функция 3-й степени - done
def Approximation_degree3(x, y):
    x, y = getData(x, y)
    SX,SX2, SX3, SX4, SX5, SX6, SY,SXY, SX2Y, SX3Y = np.sum(x), np.sum(x**2), np.sum(x**3), np.sum(x**4), np.sum(x**5), np.sum(x**6) , np.sum(y), np.sum(x*y), np.sum(x*x*y), np.sum(x*x*x*y)
    a0, a1, a2, a3 = np.linalg.solve(
        np.array([[len(x), SX, SX2, SX3], [SX, SX2, SX3, SX4], [SX2, SX3, SX4, SX5], [SX3, SX4, SX5, SX6]]),
        np.array([SY, SXY, SX2Y, SX3Y])
    ) 

     # Сумма квадратов отклонений 
    phi = a3*x**3 + a2*x**2 + a1*x + a0
    S = np.sum((phi - y) ** 2)

    return Result(
        coefficients = (a0, a1, a2),
        apply = lambda x: a3*x**3 + a2*x**2 + a1*x + a0,
        function = f'{a3:.4f}x³ + {a2:.4f}x² + {a1:.4f}x + {a0:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )    

class LnException(Exception):
    pass

# логарифмическая функция - done
def Approximation_logarith(x, y):
    x, y = getData(x, y)
    if x[x < 0]:
        raise LnException('x должен быть больше чем 0')
    X = np.log(x)
    a, b = Approximation_linear(X, y).coefficients
    phi = a*np.log(x) + b
    S = np.sum((phi - y)** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a*math.log(x) + b,
        function = f'{a:.4f}ln(x) + {b:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

#  степенная функция - done
def Approximation_power(x, y):
    x, y = getData(x,y)
    X, Y = np.log(x), np.log(y)
    B, A = Approximation_linear(X, Y).coefficients
    a, b = math.exp(A), B

    phi = a*x**b
    S = np.sum((phi - y)** 2)
    return Result(
        coefficients = (a, b),
        apply = lambda x: a*math.pow(x, b),
        function = f'{a:.4f}x^{b:.4f}',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

#  экспоненциальная функция - done
def Approximation_exp(x, y):
    x, y = getData(x, y)
    Y = np.log(y)
    B, A = Approximation_linear(x, Y).coefficients
    a, b = math.exp(A), B
    phi = a*np.exp(b*x)
    S = np.sum((phi - y)** 2)

    return Result(
        coefficients = (a, b),
        apply = lambda x: a*math.exp(b*x),
        function = f'{a:.4f}e^{b:.4f}x',
        S = S,
        deviation = msr(phi, y),
        confidence = confidence(phi, y)
    )

