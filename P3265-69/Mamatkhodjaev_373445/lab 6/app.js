document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('calculateButton').addEventListener('click', solveODE);

    function f(x, y, equation) {
        switch (equation) {
            case '1':
                return x + y;
            case '2':
                return x - Math.pow(y, 2);
            case '3':
                return y * Math.sin(x);
            default:
                return 0;
        }
    }

    function eulerMethod(x0, y0, h, xn, equation) {
        let n = Math.ceil((xn - x0) / h);
        let x = x0;
        let y = y0;
        let result = [[x, y]];

        for (let i = 0; i < n; i++) {
            y = y + h * f(x, y, equation);
            x = x + h;
            result.push([x, y]);
        }

        return result;
    }

    function improvedEulerMethod(x0, y0, h, xn, equation) {
        let n = Math.ceil((xn - x0) / h);
        let x = x0;
        let y = y0;
        let result = [[x, y]];

        for (let i = 0; i < n; i++) {
            let k1 = f(x, y, equation);
            let k2 = f(x + h, y + h * k1, equation);
            y = y + h / 2 * (k1 + k2);
            x = x + h;
            result.push([x, y]);
        }

        return result;
    }

    function milneMethod(x0, y0, h, xn, equation) {
        let initialValues = rungeKuttaMethod(x0, y0, h, equation);
        let result = [...initialValues];
        let n = Math.ceil((xn - x0) / h);

        for (let i = 4; i < n; i++) {
            let x = x0 + i * h;
            let y_predict = result[i - 4][1] + (4 * h / 3) * (2 * f(result[i - 3][0], result[i - 3][1], equation) - f(result[i - 2][0], result[i - 2][1], equation) + 2 * f(result[i - 1][0], result[i - 1][1], equation));
            let y_correct = result[i - 2][1] + (h / 3) * (f(result[i - 2][0], result[i - 2][1], equation) + 4 * f(result[i - 1][0], result[i - 1][1], equation) + f(x, y_predict, equation));
            result.push([x, y_correct]);
        }

        return result;
    }

    function rungeKuttaMethod(x0, y0, h, equation) {
        let x = x0;
        let y = y0;
        let result = [[x, y]];

        for (let i = 0; i < 3; i++) {
            let k1 = h * f(x, y, equation);
            let k2 = h * f(x + h / 2, y + k1 / 2, equation);
            let k3 = h * f(x + h / 2, y + k2 / 2, equation);
            let k4 = h * f(x + h, y + k3, equation);
            y = y + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            x = x + h;
            result.push([x, y]);
        }

        return result;
    }

    function solveODE() {
        let equation = document.getElementById('equation').value;
        let y0 = parseFloat(document.getElementById('y0').value);
        let x0 = parseFloat(document.getElementById('x0').value);
        let xn = parseFloat(document.getElementById('xn').value);
        let h = parseFloat(document.getElementById('h').value);
    
        // Проверка входных данных
        if (!validateInputs(y0, x0, xn, h)) return;
        if (!validateStepSize(h)) return;
        if (!validateRange(x0, xn)) return;
        if (!validateStepSizeRange(h)) return;
    
        let eulerResults = eulerMethod(x0, y0, h, xn, equation);
        let improvedEulerResults = improvedEulerMethod(x0, y0, h, xn, equation);
        let milneResults = milneMethod(x0, y0, h, xn, equation);
    
        let exactSolution = [];
        for (let i = 0; i <= Math.ceil((xn - x0) / h); i++) {
            let x = x0 + i * h;
            exactSolution.push([x, exactSolutionFunction(x, equation)]);
        }
    
        plotResults(eulerResults, improvedEulerResults, milneResults, exactSolution);
        showTable(eulerResults, improvedEulerResults, milneResults, exactSolution);
    }

    function validateInputs(y0, x0, xn, h) {
        if (isNaN(y0) || isNaN(x0) || isNaN(xn) || isNaN(h) || y0 === '' || x0 === '' || xn === '' || h === '') {
            alert("Пожалуйста, введите корректные числовые значения для всех входных данных.");
            return false;
        }
        return true;
    }
    
    function validateStepSize(h) {
        if (h <= 0) {
            alert("Шаг (h) должен быть положительным числом.");
            return false;
        }
        return true;
    }
    
    function validateRange(x0, xn) {
        if (x0 >= xn) {
            alert("Начальное значение x0 должно быть меньше конечного значения xn.");
            return false;
        }
        return true;
    }
    
    function validateStepSizeRange(h) {
        if (h < 0.00001 || h > 1) {
            alert("Шаг (h) должен быть в пределах от 0.00001 до 1.");
            return false;
        }
        return true;
    }
    
    

    function exactSolutionFunction(x, equation) {
        switch (equation) {
            case '1':
                return 2 * Math.exp(x) - x - 1;
            case '2':
                return Math.sqrt(Math.log(Math.exp(1) + 2*x + x**2));
            case '3':
                return Math.exp(1 - Math.cos(x));
            default:
                return 0;
        }
    }

    function plotResults(eulerResults, improvedEulerResults, milneResults, exactSolution) {
        let eulerTrace = {
            x: eulerResults.map(pair => pair[0]),
            y: eulerResults.map(pair => pair[1]),
            mode: 'lines',
            name: 'Euler'
        };

        let improvedEulerTrace = {
            x: improvedEulerResults.map(pair => pair[0]),
            y: improvedEulerResults.map(pair => pair[1]),
            mode: 'lines',
            name: 'Improved Euler'
        };

        let milneTrace = {
            x: milneResults.map(pair => pair[0]),
            y: milneResults.map(pair => pair[1]),
            mode: 'lines',
            name: 'Milne'
        };

        let exactTrace = {
            x: exactSolution.map(pair => pair[0]),
            y: exactSolution.map(pair => pair[1]),
            mode: 'lines',
            name: 'Exact'
        };

        let data = [eulerTrace, improvedEulerTrace, milneTrace, exactTrace];

        Plotly.newPlot('plot', data);
    }

    function showTable(eulerResults, improvedEulerResults, milneResults, exactSolution) {
        let minLength = Math.min(eulerResults.length, improvedEulerResults.length, milneResults.length, exactSolution.length);
        let table = '<table border="1"><tr><th>x</th><th>Euler</th><th>Improved Euler</th><th>Milne</th><th>Exact</th></tr>';
        for (let i = 0; i < minLength; i++) {
            table += `<tr>
                        <td>${eulerResults[i][0].toFixed(3)}</td>
                        <td>${eulerResults[i][1].toFixed(3)}</td>
                        <td>${improvedEulerResults[i][1].toFixed(3)}</td>
                        <td>${milneResults[i][1].toFixed(3)}</td>
                        <td>${exactSolution[i][1].toFixed(3)}</td>
                      </tr>`;
        }
        table += '</table>';
        document.getElementById('results').innerHTML = table;
    }
});
