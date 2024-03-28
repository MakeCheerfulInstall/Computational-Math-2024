export const functions = [
    {
        value: (x) => 3 * (x ** 3) + 1.7 * (x ** 2) - 15.42 * x + 6.89,
        derivative: (x) => -15.42 + 3.4 * x + 9 * (x ** 2),
        phi: (x) => -0.107991*(x**3) - 0.0611949*(x**2) + 1.55507374*x - 0.24801933,
        phiDerivative: (x) => 1.55507 - 0.12239 * x - 0.323973 * (x**2),
        html: '3x<sup>3</sup> + 1.7x<sup>2</sup> - 15.42x + 6.89',
        stroke: '3*x*x*x + 1.7*x*x - 15.42*x + 6.89'
    },
    {
        value: (x) => 5 * Math.sin(x) - 10 * x,
        derivative: (x) => 5 * Math.cos(x) - 10,
        phi: (x) => 0.5 * Math.sin(x),
        phiDerivative: (x) => 0.5 * Math.cos(x),
        html: '5sin(x) - 10x',
        stroke: '5*sin(x) - 10*x',
    },
    {
        value: (x) => 9 * (Math.pow(x, 2)) - 6 * x + 1,
        derivative: (x) => 18 * x - 6,
        phi: (x) => 1.5 * (x ** 2) + 1 / 6,
        phiDerivative: (x) => 3 * x,
        html: '9x<sup>2</sup> - 6x + 1',
        stroke: '9 * x * x - 6 * x + 1'
    },
];
export const systems = [
    {
        // из презентации на Ньютона
        f: {
            value: (x, y) => x * x + y * y - 4,
            derivativeX: (x, y) => 2 * x,
            derivativeY: (x, y) => 2 * y,
            html: 'x<sup>2</sup> + y<sup>2</sup> - 4',
            stroke: 'x * x + y * y - 4',
        },
        g: {
            value: (x, y) => y - 3 * x * x,
            derivativeX: (x, y) => -6 * x,
            derivativeY: (x, y) => 1,
            html: 'y - 3 * x<sup>2</sup>',
            stroke: 'y - 3 * x * x',
        },
    },
    { // 2 вариант
        f: {
            value: (x, y) => Math.tan(x * y) - x ** 2,
            derivativeX: (x, y) => y * (1 / (Math.cos(x * y)) ** 2) - 2 * x,
            derivativeY: (x, y) => x * (1 / (Math.cos(x * y)) ** 2),
            html: 'tan(x*y) - x<sup>2</sup>',
            stroke: 'tan(x * y) + x * x'
        },
        g: {
            value: (x, y) => 0.8 * (x ** 2) + 2 * (y ** 2) - 1,
            derivativeX: (x, y) => 1.6 * x,
            derivativeY: (x, y) => 4 * y,
            html: '0.8x<sup>2</sup> + 2y<sup>2</sup> - 1',
            stroke: '0.8 * x * x + 2 * y * y - 1'
        },
    },
    {
        f: {
            value: (x, y) => Math.cos(y - 2) + x,
            derivativeX: (x, y) => 1,
            derivativeY: (x, y) => -Math.sin(y - 2),
            html: 'cos(y - 2) + x',
            stroke: 'cos(y - 2) + x'
        },
        g: {
            value: (x, y) => Math.sin(x + 0.5) - y - 1,
            derivativeX: (x, y) => Math.cos(x + 0.5),
            derivativeY: (x, y) => -1,
            html: 'sin(x + 0.5) - y - 1',
            stroke: 'sin(x + 0.5) - y - 1'
        }
    }
];

export function checkIsInsideInterval(x, data) {
    if (!(data.start_x <= x && x <= data.end_y)) {
        alert("Последовательность выходит за пределы установленного отрезка. " +
            "Выбранный метод не подходит для решения данного уравнения на этом отрезке. " +
            "Выводим значение последней итерации");
        return false;
    }
    return true;
}

export function checkDeltaMoreEpsilon(a, b, x, data) {
    if (Math.abs(data.functionData.value(x)) < data.epsilon) {
        return false;
    } else if (Math.abs(a - b) < Math.pow(0.1, 8)) {
        alert("Нет корней на данном отрезке. " +
            "Выводим значение последней итерации");
        return false;
    }
    return true;
}
export function checkInterval(a, b, x, data) {
    if (Math.abs(data.functionData.value(x)) < data.epsilon || Math.abs(a - b) < data.epsilon) {
        return false;
    } else if (data.functionData.value(a)*data.functionData.value(b) > 0) {
        alert("Нет корней на данном отрезке. " +
            "Выводим значение последней итерации");
        return false;
    }
    return true;
}

export function handleOutEpsilonByIterationsCount(data, iterations, fValue) {
    if (fValue && iterations === 20 && Math.abs(fValue) >= data.epsilon) {
        alert("Значение недостаточно точное из-за недостаточного допустимого количества итераций. " +
            "Выводим значение последней итерации");
    }
}

