const numeric = require('numeric');

// Уравнения системы
const equations = [
    (x, y) => x**2 + y**2 - 4,
    (x, y) => x**2 - y - 1
];

// Производные уравнений по x
const derivativesX = [
    (x, y) => 2*x,
    (x, y) => 2*x
];

// Производные уравнений по y
const derivativesY = [
    (x, y) => 2*y,
    (x, y) => -1
];

// Функция для решения системы методом Ньютона
function newtonMethod(equations, derivativesX, derivativesY, initialGuess, tolerance, maxIterations) {
    let x = initialGuess[0];
    let y = initialGuess[1];
    let iteration = 0;
    let errors = [];

    while (iteration < maxIterations) {
        const f = equations.map(eq => eq(x, y));
        const J = [
            [derivativesX[0](x, y), derivativesY[0](x, y)],
            [derivativesX[1](x, y), derivativesY[1](x, y)]
        ];
        const JInverse = numeric.inv(J);
        const correction = numeric.dot(JInverse, f);
        x -= correction[0];
        y -= correction[1];
        const error = Math.max(...f.map(Math.abs));
        errors.push(error);
        iteration++;

        if (error < tolerance) {
            console.log("Solution found:");
            console.log("x =", x);
            console.log("y =", y);
            console.log("Iterations:", iteration);
            console.log("Errors:", errors);
            return [x, y];
        }
    }

    console.log("Solution not found within maximum iterations.");
    return null;
}

// Ввод начальных приближений с клавиатуры
const initialGuess = [];
const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
});

readline.question('Enter initial guess for x: ', x => {
    initialGuess.push(parseFloat(x));
    readline.question('Enter initial guess for y: ', y => {
        initialGuess.push(parseFloat(y));
        readline.close();

        const solution = newtonMethod(equations, derivativesX, derivativesY, initialGuess, 0.01, 100);
        if (solution) {
            // Вывод вектора неизвестных
            console.log("Solution vector:", solution);
            
            // Вывод графика функций
            // Тут нужно использовать соответствующую библиотеку для построения графиков на JavaScript
            // Например, можно воспользоваться библиотекой Chart.js или D3.js
            // Пример: https://www.chartjs.org/docs/latest/
        }
    });
});
