
// Функция для проверки и достижения диагонального преобладания
function rearrangeRowsForDiagonalDominance(matrix, freeDigits) {
    const n = matrix.length;
    let A = matrix.map(row => [...row]); // Создаем копию матрицы
    let b = freeDigits.slice(0);

    // Функция находит максимальный элемент в строке
    function findMaxIndex(row) {
        let maxIndex = 0;
        let maxValue = Math.abs(row[0]);
        for (let i = 1; i < row.length; i++) {
            if (Math.abs(row[i]) > maxValue) {
                maxIndex = i;
                maxValue = Math.abs(row[i]);
            }
        }
        return maxIndex;
    }

    // Проверка на диагональное преобладание и коррекция, если необходимо
    for (let i = 0; i < n; i++) {
        let row = A[i];
        let maxIndex = findMaxIndex(row);
        let sum = row.reduce((acc, val, idx) => idx !== maxIndex ? acc + Math.abs(val) : acc, 0);
        debugger;
        if (Math.abs(row[maxIndex]) <= sum) {
            diagonalDominance = false;
            // меняем главную диагональ
            A[i][i] = (sum) + 1;
        }
    }

    // Ходим по строкам в цикле, пока не обеспечим диагональное доминирование

    for (let i = 0; i < n; i++) {
        let row = A[i];
        let maxIndex = findMaxIndex(row);
        let sum = row.reduce((acc, val, idx) => idx !== maxIndex ? acc + Math.abs(val) : acc, 0);
        debugger;
        if (Math.abs(row[maxIndex]) <= sum) {
            throw 'Диагональное доминирование невозможно в связи с отсутствием наибольшего элемента в строке';
        }
        if (maxIndex !== i) {
            // Меняем строки местами
            if (maxIndex === findMaxIndex(A[maxIndex])) {
                throw 'Невозможно достичь диагонального доминирования';
            }
            let temp = A[i];
            let tempb = b[i];
            A[i] = A[maxIndex];
            b[i] = b[maxIndex];
            A[maxIndex] = temp;
            b[maxIndex] = tempb;
            i--; // Остаемся на той же строке после смены
        }
    }

    let arr = [A, b];
    return arr;
}


function readDataFromPrompt() {
    const n = parseInt(prompt('Введите размерность матрицы:'));
    let A = [];
    let b = [];
    let tolerance, maxIterations;

    // Ввод матрицы A
    console.log('Введите элементы матрицы A:');
    for (let i = 0; i < n; i++) {
        const row = prompt(`Введите ${n} элементов для ${i + 1}-й строки, разделенные пробелом:`);
        A.push(row.split(' ').map(parseFloat));
    }

    // Ввод вектора b
    const vector = prompt(`Введите ${n} элементов для вектора b, разделенные пробелом:`);
    b = vector.split(' ').map(parseFloat);

    // Ввод допустимой погрешности
    tolerance = parseFloat(prompt('Введите допустимую погрешность (tolerance):'));

    // Ввод максимального количества итераций
    maxIterations = parseInt(prompt('Введите максимальное количество итераций:'));

    return { A, b, tolerance, maxIterations };
}

// Реализация метода Гаусса-Зейделя
function gaussSeidel(matrix, b, size, epsilon) {
    let x = new Array(size).fill(0);
    let iterations = 0;
    let errorVector = new Array(size),
        maxErrors = new Array();

    while (true) {
        iterations++;
        let newX = [...x];
        for (let i = 0; i < size; i++) {
            let sum = 0;
            for (let j = 0; j < size; j++) {
                if (i !== j) {
                    sum += matrix[i][j] * newX[j], 3;
                }
            }
            newX[i] = ((b[i] - sum) / matrix[i][i]).toFixed(4);
            errorVector[i] = (Math.abs(newX[i] - x[i])).toFixed(4);
        }

        let maxError = Math.max(...errorVector);
        maxErrors.push(maxError);
        if (maxError < epsilon) {
            break;
        }
        x = newX;
    }

    return { solution: x, iterations, maxErrors};
}

let size;
let epsilon;
let matrix = [];
let b = [];

let readingMethod = prompt("Введите 1 для получения информации из файла или 2, для введения информации вручную:");

if(readingMethod === '1'){
    let input = document.createElement("input");
    let title = document.querySelector("main_title");
    document.body.insertBefore(input, title);
    input.setAttribute("type", "file");

    input.addEventListener('change', function(){
        let fr = new FileReader();
        fr.onload = function () {
                let newArr = JSON.stringify(fr.result.trim()).split("_");
                console.log(newArr);
                size = parseInt(newArr[1]);
                epsilon = parseFloat(newArr[2]);
                console.log(size, epsilon);
                for(let i = 3; i < 6; i++){
                    matrix[i - 3] = newArr[i].split(" ").map(parseFloat);
                }
                console.log(matrix.length);
                b = newArr[6].split(" ").map(parseFloat);
                console.log(b.length, epsilon, size);  
                let res = gaussSeidel(matrix, b, size, epsilon);
                alert(`Решение: ${res.solution}\nИтераций: ${res.iterations}\nВектор погрешностей: ${res.maxErrors}`);    
        }
        fr.readAsText(this.files[0]);  
    })

    
    
} else {
// Получение размера матрицы и точности от пользователя
size = parseInt(prompt("Введите размер матрицы:"));
epsilon = parseFloat(prompt("Введите точность:"));


// Выбор способа заполнения матрицы
let fillOption = prompt("Введите 1 для ручного ввода коэффициентов или 2 для генерации случайных коэффициентов:");

if (fillOption === "1") {
    for (let i = 0; i < size; i++) {
        matrix[i] = [];
        for (let j = 0; j < size; j++) {
            matrix[i][j] = parseFloat(prompt(`Введите элемент матрицы [${i + 1}][${j + 1}]:`));
        }
        b[i] = parseFloat(prompt(`Введите свободный член ${i + 1}:`));
    }

    let arr = rearrangeRowsForDiagonalDominance(matrix, b);

    matrix = arr[0];
    b = arr[1];

    console.log(matrix);
    console.log(b);

    let result = gaussSeidel(matrix, b, size, epsilon);
    alert(`Решение: ${result.solution}\nИтераций: ${result.iterations}\nВектор погрешностей: ${result.maxErrors}`);

} else if (fillOption === "2") {
    for (let i = 0; i < size; i++) {
        matrix[i] = [];
        for (let j = 0; j < size; j++) {
            matrix[i][j] = Math.floor(Math.random() * 20) - 10; // Генерация чисел от -10 до 10
        }
        b[i] = Math.floor(Math.random() * 20) - 10;
    }

    for (let i = 0; i < size; i++) {
        let row = matrix[i];
        let index = matrix[i][i];
        debugger;
        let sum = row.reduce((acc, val, idx) => idx !== i ? acc + Math.abs(val) : acc, 0);

        diagonalDominance = false;
        // меняем главную диагональ
        matrix[i][i] = (sum) + 1;

    }

    let arr = rearrangeRowsForDiagonalDominance(matrix, b);

    console.log(arr);

    matrix = arr[0];
    b = arr[1];

    console.log(matrix);
    console.log(b);

    let result = gaussSeidel(matrix, b, size, epsilon);
    alert(`Решение: ${result.solution}\nИтераций: ${result.iterations}\nВектор погрешностей: ${result.maxErrors}`);
}

console.log(matrix);
console.log(b);
}

