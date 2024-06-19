document.getElementById('dataForm').addEventListener('submit', (event) => event.preventDefault());

function loadFile() {
    const fileInput = document.getElementById('fileInput');
    if (!fileInput.files.length) {
        alert('Пожалуйста выберете файл!');
        return;
    }

    const file = fileInput.files[0];
    const reader = new FileReader();

    reader.onload = function(event) {
        console.log("Файл загружен, содержимое:", event.target.result);
        const data = parseInput(event.target.result);
        processData(data);
    };

    reader.onerror = function() {
        alert('Невозможно прочитать файл');
    };

    reader.readAsText(file);
}

function useTextInput() {
    const input = document.getElementById('dataInput').value;
    const data = parseInput(input);
    processData(data);
}

function parseInput(input) {
    return input.trim().split('\n').map(line => {
        const [x, y] = line.split(',');
        return { x: parseFloat(x), y: parseFloat(y) };
    });
}

function calculateRSquared(data, predictions) {
    const meanY = data.reduce((sum, pt) => sum + pt.y, 0) / data.length;
    const ssTot = data.reduce((sum, pt) => sum + Math.pow(pt.y - meanY, 2), 0);
    const ssRes = data.reduce((sum, pt, index) => sum + Math.pow(pt.y - predictions[index], 2), 0);
    return 1 - ssRes / ssTot;
}

function linearRegression(data) {
    let n = data.length;
    let sumX = 0, sumY = 0, sumXY = 0, sumXX = 0, sumYY = 0;

    data.forEach(point => {
        sumX += point.x;
        sumY += point.y;
        sumXY += point.x * point.y;
        sumXX += point.x ** 2;
        sumYY += point.y ** 2;
    });

    const slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX ** 2);
    const intercept = (sumY - slope * sumX) / n;
    const correlation = (n * sumXY - sumX * sumY) / Math.sqrt((n * sumXX - sumX ** 2) * (n * sumYY - sumY ** 2));
    const predictions = data.map(pt => slope * pt.x + intercept);
    const rSquared = calculateRSquared(data, predictions);

    return {
        rSquared: rSquared.toFixed(4),
        formula: `y = ${slope.toFixed(2)}x + ${intercept.toFixed(2)}`,
        intercept: intercept,
        correlation: correlation,
        slope: slope,
        points: data.map(point => ({
            x: point.x,
            y: slope * point.x + intercept,
            originalY: point.y
        }))
    };
}

function processData(data) {
    if (data.length < 8 || data.length > 12) {
        alert('Пожалуйств введите от 8 до 12 точек.');
        return;
    }

    const outputElement = document.getElementById('output');
    outputElement.innerHTML = ''; // Clear previous output

    // Perform approximations and display results
    displayResults(linearRegression(data), 'Линейная зависимость', data, outputElement, 'linearPlot');
    displayResults(exponentialRegression(data), 'Экспоненциальная зависимость', data, outputElement, 'exponentialPlot');
    displayResults(powerRegression(data), 'Степенная зависимость', data, outputElement, 'powerPlot');
    displayResults(logarithmicRegression(data), 'Логарифмическая зависимость', data, outputElement, 'logarithmicPlot');
    displayResults(polynomialRegression(data, 2), 'Полиноминальная зависимость второго порядка', data, outputElement, 'polynomial2Plot');
    displayResults(polynomialRegression(data, 3), 'Полиноминальная зависимость третьего порядка', data, outputElement, 'polynomial3Plot');
}

// Existing functions: parseInput(), linearRegression()

function exponentialRegression(data) {
    // Filtering out zero values and negative values which cause issues with logarithms
    const filteredData = data.filter(pt => pt.y > 0);
    const logData = filteredData.map(pt => ({ x: pt.x, y: Math.log(pt.y) }));
    const linearModel = linearRegression(logData);
    const a = Math.exp(linearModel.intercept);
    const predictions = filteredData.map(pt => a * Math.exp(linearModel.slope * pt.x));

    const rSquared = calculateRSquared(filteredData, predictions);


    return {
        rSquared: rSquared.toFixed(4),
        formula: `y = ${a.toFixed(2)}e^(${linearModel.slope.toFixed(2)}x)`,
        points: filteredData.map(pt => ({ x: pt.x, y: a * Math.exp(linearModel.slope * pt.x), originalY: pt.y }))
    };
}

function powerRegression(data) {
    // Filtering out zero values for x and y
    const filteredData = data.filter(pt => pt.x > 0 && pt.y > 0);
    const logData = filteredData.map(pt => ({ x: Math.log(pt.x), y: Math.log(pt.y) }));
    const linearModel = linearRegression(logData);
    const a = Math.exp(linearModel.intercept);
    const predictions = filteredData.map(pt => a * Math.pow(pt.x, linearModel.slope));

    const rSquared = calculateRSquared(filteredData, predictions);

    return {
        formula: `y = ${a.toFixed(2)}x^${linearModel.slope.toFixed(2)}`,
        points: filteredData.map(pt => ({ x: pt.x, y: a * Math.pow(pt.x, linearModel.slope), originalY: pt.y })),
        rSquared: rSquared.toFixed(4)
    };
}

function logarithmicRegression(data) {
    const filteredData = data.filter(pt => pt.x > 0);
    const logData = filteredData.map(pt => ({ x: Math.log(pt.x), y: pt.y }));
    const linearModel = linearRegression(logData);
    const predictions = filteredData.map(pt => linearModel.intercept + linearModel.slope * Math.log(pt.x));

    const rSquared = calculateRSquared(filteredData, predictions);

    return {
        formula: `y = ${linearModel.intercept.toFixed(2)} + ${linearModel.slope.toFixed(2)}ln(x)`,
        points: filteredData.map(pt => ({ x: pt.x, y: linearModel.intercept + linearModel.slope * Math.log(pt.x), originalY: pt.y })),
        rSquared: rSquared.toFixed(4)
    };
}

function polynomialRegression(data, degree) {
    const X = data.map(pt => Array.from({length: degree + 1}, (_, i) => Math.pow(pt.x, i)));
    const Y = data.map(pt => [pt.y]);

    // Использование numeric.js для вычисления псевдообратной матрицы
    const XT = numeric.transpose(X);
    const XTX = numeric.dot(XT, X);
    const XTY = numeric.dot(XT, Y);

    // Вычисление псевдообратной матрицы XTX
    const pinv = numeric.inv(XTX);
    const coefficients = numeric.dot(pinv, XTY).map(row => row[0]);

    const predictions = X.map(row => 
        coefficients.reduce((acc, coeff, index) => acc + coeff * Math.pow(row[1], index), 0)
    );

    const rSquared = calculateRSquared(data, predictions);

    return {
        formula: `y = ${coefficients.map((c, i) => `${c.toFixed(2)}x^${i}`).join(' + ')}`,
        points: data.map((pt, i) => ({
            x: pt.x,
            y: predictions[i],
            originalY: pt.y
        })),
        rSquared: rSquared.toFixed(4)
    };
}

function transpose(matrix) {
    return matrix[0].map((_, colIndex) => matrix.map(row => row[colIndex]));
}

function multiplyMatrices(a, b) {
    const bT = transpose(b);
    return a.map(row => {
        return bT.map(col => {
            return row.reduce((sum, elm, k) => sum + elm * col[k], 0);
        });
    });
}


function solveGaussianElimination(A, B) {
    let n = A.length;
    for (let i = 0; i < n; i++) {
        // Search for maximum in this column
        let maxEl = Math.abs(A[i][i]);
        let maxRow = i;
        for (let k = i + 1; k < n; k++) {
            if (Math.abs(A[k][i]) > maxEl) {
                maxEl = Math.abs(A[k][i]);
                maxRow = k;
            }
        }

        // Swap maximum row with current row (column by column)
        for (let k = i; k < n; k++) {  // Изменил условие с k < n + 1 на k < n
            let tmp = A[maxRow][k];
            A[maxRow][k] = A[i][k];
            A[i][k] = tmp;
        }

        let tmpB = B[maxRow];
        B[maxRow] = B[i];
        B[i] = tmpB;

        // Make all rows below this one 0 in current column
        for (let k = i + 1; k < n; k++) {
            let c = -A[k][i] / A[i][i];
            for (let j = i; j < n; j++) {
                if (i === j) {
                    A[k][j] = 0;
                } else {
                    A[k][j] += c * A[i][j];
                }
            }
            B[k][0] += c * B[i][0];
        }
    }

    // Solve equation Ax=b for an upper triangular matrix A
    let x = new Array(n).fill(0);
    for (let i = n - 1; i >= 0; i--) {
        x[i] = B[i][0] / A[i][i];
        for (let k = i - 1; k >= 0; k--) {
            B[k][0] -= A[k][i] * x[i];
        }
    }
    return x;
}

function plotData(points, title, plotId) {
    const trace1 = {
        x: points.map(p => p.x),
        y: points.map(p => p.originalY),
        mode: 'markers',
        type: 'scatter',
        name: 'Original Data'
    };

    const trace2 = {
        x: points.map(p => p.x),
        y: points.map(p => p.y),
        mode: 'lines',
        type: 'scatter',
        name: 'Fitted Model'
    };

    const layout = {
        title: title,
        xaxis: {
            title: 'X-axis'
        },
        yaxis: {
            title: 'Y-axis',
            autorange: true
        }
    };

    Plotly.newPlot(plotId, [trace1, trace2], layout);
}

// В функции displayResults добавляем аргумент plotId и передаем его в plotData
function displayResults(model, title, data, outputElement, plotId) {
    const modelResults = document.createElement('div');
    // Общий вывод модели и коэффициента детерминации
    let modelInfo = `<h2>${title}</h2><p>формула: ${model.formula}</p><p>R²: ${model.rSquared}</p>`;

    // Добавляем вывод коэффициента корреляции только для линейной регрессии
    if (title === 'Линейная зависимость') {
        modelInfo += `<p>Коэффициент корреляции Пиирсона: ${model.correlation.toFixed(4)}</p>`;
    }

    modelResults.innerHTML = modelInfo;
    outputElement.appendChild(modelResults);

    plotData(model.points, title, plotId);
}



