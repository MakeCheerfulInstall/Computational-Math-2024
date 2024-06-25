// Определение функции уравнения
function equationFunction(x) {
    return x**3 + 4.81*x**2 - 17.37*x + 5.38;
}

// Метод половинного деления
function bisectionMethod(a, b, tolerance) {
    let iteration = 0;
    let c = (a + b) / 2;
    let fc = equationFunction(c);

    while (Math.abs(fc) > tolerance) {
        c = (a + b) / 2;
        fc = equationFunction(c);

        if (equationFunction(a) * fc < 0) {
            b = c;
        } else {
            a = c;
        }

        iteration++;
    }

    return { root: c, iterations: iteration };
}

// Метод секущих
function secantMethod(x0, x1, tolerance) {
    let iteration = 0;
    let x = x1;
    let fx = equationFunction(x);

    while (Math.abs(fx) > tolerance) {
        const xNew = x - (fx * (x - x0)) / (fx - equationFunction(x0));
        x0 = x;
        x = xNew;
        fx = equationFunction(x);

        iteration++;
    }

    return { root: x, iterations: iteration };
}

// Метод простой итерации
function simpleIterationMethod(a, b, tolerance) {
    // Проверка условия сходимости метода простой итерации
    const lambda = 1 / Math.max(
        Math.abs(equationFunction(a)),
        Math.abs(equationFunction(b))
    );
    if (lambda >= 1) {
        throw new Error("Метод простой итерации не сходится на заданном интервале.");
    }

    let iteration = 0;
    let x = (a + b) / 2;
    let fx = equationFunction(x);

    while (Math.abs(fx) > tolerance && iteration) {
        x = equationFunction(x);
        fx = equationFunction(x);

        iteration++;
    }

    return { root: x, iterations: iteration };
}

// Функция для проверки наличия корня на заданном интервале
function checkRootExistence(a, b) {
    let signChange = false;
    let prevSign = Math.sign(equationFunction(a));

    for (let x = a + 0.1; x <= b; x += 0.1) {
        const sign = Math.sign(equationFunction(x));
        if (sign !== prevSign) {
            if (signChange) {
                // Если найдено второе изменение знака, значит, есть более одного корня
                return false;
            }
            signChange = true;
        }
        prevSign = sign;
    }

    // Проверяем знаки на концах интервала
    return equationFunction(a) * equationFunction(b) < 0;
}

// Функция для поиска начального приближения к корню
function findInitialApproximation(a, b) {
    const fa = equationFunction(a);
    const fb = equationFunction(b);

    if (Math.abs(fa) < Math.abs(fb)) {
        return [a, a + (b - a) / 2];
    } else {
        return [b - (b - a) / 2, b];
    }
}

// Функция для вывода результатов
function printResults(root, functionValue, iterations) {
    console.log("Найденный корень уравнения:", root);
    console.log("Значение функции в корне:", functionValue);
    console.log("Количество итераций:", iterations);
}


// Функция для вывода графика на страницу
function plotFunction() {
    const canvas = document.getElementById("graphCanvas");
    const ctx = canvas.getContext("2d");

    // Очистка холста
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Определение размеров графика
    const minX = -10;
    const maxX = 10;
    const minY = -10;
    const maxY = 10;
    const step = 0.001;

    // Создание графика функции
    ctx.beginPath();
    ctx.moveTo(0, canvas.height / 2); // Перемещение в начало координат
    for (let x = minX; x <= maxX; x += step) {
        const y = equationFunction(x);
        const plotX = (x - minX) / (maxX - minX) * canvas.width;
        const plotY = canvas.height - (y - minY) / (maxY - minY) * canvas.height;
        ctx.lineTo(plotX, plotY);
    }
    ctx.stroke();

    // Отрисовка осей координат
    ctx.beginPath();
    ctx.moveTo(0, canvas.height / 2);
    ctx.lineTo(canvas.width, canvas.height / 2);
    ctx.moveTo(canvas.width / 2, 0);
    ctx.lineTo(canvas.width / 2, canvas.height);
    ctx.stroke();

    // Отрисовка точек пересечения функции с осями координат
    const rootX = findRootsWithAxesIntersection(minX, maxX);
    const rootY = findRootsWithAxesIntersection(minY, maxY);
    ctx.fillStyle = "red";
    ctx.beginPath();
    rootX.forEach(root => {
        const plotX = (root - minX) / (maxX - minX) * canvas.width;
        ctx.arc(plotX, canvas.height / 2, 3, 0, Math.PI * 2);
    });
    ctx.fill();
}

// Функция для поиска точек пересечения функции с осями координат
function findRootsWithAxesIntersection(min, max) {
    const roots = [];
    if (equationFunction(min) * equationFunction(max) <= 0) {
        roots.push(min);
    }
    for (let x = min; x <= max; x += 0.1) {
        if (equationFunction(x) * equationFunction(x + 0.1) <= 0) {
            roots.push(x + 0.1);
        }
    }
    return roots;
}


// Пример использования
const a = -7.5;
const b = -6.5;
const tolerance = 0.01;

if (checkRootExistence(a, b)) {
    const initialApproximation = findInitialApproximation(a, b);
    
    const rootBisection = bisectionMethod(a, b, tolerance);
    const rootSecant = secantMethod(initialApproximation[0], initialApproximation[1], tolerance);
    const rootSimpleIteration = simpleIterationMethod(a, b, tolerance);
    
    console.log("Метод половинного деления");
    printResults(rootBisection.root, equationFunction(rootBisection.root), rootBisection.iterations);
    console.log("Метод секущих");
    printResults(rootSecant.root, equationFunction(rootSecant.root), rootSecant.iterations);
    console.log("Метод простых итераций");
    printResults(rootSimpleIteration.root, equationFunction(rootSimpleIteration.root), rootSimpleIteration.iterations);
} else {
    console.log("На указанном интервале корней нет или их несколько.");
}

plotFunction();
