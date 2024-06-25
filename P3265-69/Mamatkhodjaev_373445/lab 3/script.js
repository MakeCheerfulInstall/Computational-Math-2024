// Функция для вычисления значения интегрируемой функции
function func(x, number) {
    switch (number){
        case '1':
            return x ** 2;
        case '2':
            return -2*x**3 - 5*x**2 + 7*x - 13;
    }
    
}

// Функция для метода прямоугольников (левых, правых, средних)
function rectangleMethod(func, funcNumber, a, b, n, type) {
    const h = (b - a) / n;
    let sum = 0;
    for (let i = 0; i < n; i++) {
        let xi = a + i * h;
        if (type === 'left') {
            sum += func(xi, funcNumber);
        } else if (type === 'right') {
            sum += func(xi + h, funcNumber);
        } else if (type === 'middle') {
            sum += func(xi + h / 2, funcNumber);
        }
    }
    return sum * h;
}

// Функция для метода трапеций
function trapezoidMethod(func, funcNumber, a, b, n) {
    const h = (b - a) / n;
    let sum = (func(a, funcNumber) + func(b, funcNumber)) / 2;
    for (let i = 1; i < n; i++) {
        let xi = a + i * h;
        sum += func(xi, funcNumber);
    }
    return sum * h;
}

// Функция для метода Симпсона
function simpsonMethod(func, funcNumber, a, b, n) {
    const h = (b - a) / n;
    let sum = func(a, funcNumber) + func(b, funcNumber);
    for (let i = 1; i < n; i += 2) {
        let xi = a + i * h;
        sum += 4 * func(xi, funcNumber);
    }
    for (let i = 2; i < n - 1; i += 2) {
        let xi = a + i * h;
        sum += 2 * func(xi, funcNumber);
    }
    return (h / 3) * sum;
}

// Функция для вычисления интеграла с заданной точностью
function computeIntegral(func, funcNumber, a, b, tolerance, method) {
    let n = 4;
    let integralPrev = 0;
    let integralCurr = method(func, funcNumber, a, b, n);
    do {
        n *= 2;
        integralPrev = integralCurr;
        integralCurr = method(func, funcNumber, a, b, n);
    } while (Math.abs(integralCurr - integralPrev) / integralCurr > tolerance);
    return { value: integralCurr, partitions: n };
}

// Функция для проверки сходимости несобственного интеграла второго рода
function checkConvergence(func, funcNumber, a, b) {
    // Проверяем сходимость интеграла с помощью признака сходимости
    // Например, можем использовать признак Лейбница или признак Дирихле
    // В данном примере просто проверяем, что интеграл существует, если функция не расходится к бесконечности
    const M = Math.max(Math.abs(func(a, funcNumber)), Math.abs(func(b, funcNumber)));
    if (M === Infinity) {
        return false; // Интеграл расходится
    }
    return true; // Интеграл сходится
}

// Функция для вычисления несобственного интеграла второго рода
function computeImproperIntegral(func, funcNumber, a, b, tolerance, method) {
    if (!checkConvergence(func, funcNumber, a, b)) {
        return "Интеграл не существует";
    }
    return computeIntegral(func, funcNumber, a, b, tolerance, method);
}

const a = parseFloat(prompt("Введите нижний предел интегрирования:"));
const b = parseFloat(prompt("Введите верхний предел интегрирования:"));
const tolerance = parseFloat(prompt("Введите требуемую точность:"));

const method = prompt("Выберите метод интегрирования: (left, right, middle, trapezoid, simpson)");

const funcNumber = prompt("Введите номер функции интеграл, которой, требуется вычислить\n" + 
                            "1 : x^2\n" + 
                            "2 : -2*x^3 - 5*x^2 + 7*x - 13");

let result;
switch (method) {
    case 'left':
    case 'right':
    case 'middle':
        result = computeImproperIntegral(func, funcNumber, a, b, tolerance, (x, funcNumber, a, b, n) => rectangleMethod(x, funcNumber, a, b, n, method));
        break;
    case 'trapezoid':
        result = computeImproperIntegral(func, funcNumber, a, b, tolerance, trapezoidMethod);
        break;
    case 'simpson':
        result = computeImproperIntegral(func, funcNumber, a, b, tolerance, simpsonMethod);
}

console.log(`Значение интеграла: ${result.value}`);
console.log(`Число разбиений интервала интегрирования: ${result.partitions}`);
