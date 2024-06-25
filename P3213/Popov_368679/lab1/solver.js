
/** Максимальное допустимое data.size. */
export const MAX_N = 20;

/**
 * Результат решения СЛАУ.
 * @property determinate - определитель матрицы
 * @property solution - вектор найденных неизвестных.
 * @property errordata.rightValuess - векторы невязок.
 */
export class Result {
    constructor(matrix,determinate,solution,residuals) {
        this.matrix = matrix;
        this.determinate = determinate;
        this.solution = solution;
        this.residuals = residuals;
    }
}

/**
 * Получить решение СЛАУ с помощью прямого метода Гаусса.
 */
export function solveLinearEquationsSystem(data) {
    forward(data);
    const solution = back(data);
    const residualsVectors= residuals(data)
    const det = determinant(data);
    console.log(det)
    const tri = JSON.parse(JSON.stringify(data.matrix));
    tri.map((row,index) => {
        row.push(data.rightValues[index])
    })
    return new Result(tri,det,solution,residualsVectors);
}

/** Если в процессе вычисления a11, a22, a33, ... = 0 - нужно переставить соответственно коэф. */
const swapRows = (i, j, data) =>{

    let tempRow = data.matrix[i];
    data.matrix[i] = data.matrix[j];
    data.matrix[j] = tempRow;

    let tempVal = data.rightValues[i];
    data.rightValues[i] = data.rightValues[j];
    data.rightValues[j] = tempVal;
    data.swap +=1;
}
/** Прямой ход метода Гаусса */
const forward = (data) =>{
    for (let i = 0; i < data.size; i++) {
        // Проверяем, является ли диагональный элемент очень близким к нулю
        if (data.matrix[i][i] === 0) {
            // Переставляем текущее уравнение с другим уравнением, где диагональный элемент не равен нулю
            let found = false;
            for (let k = i + 1; k < data.size; k++) {
                if (data.matrix[k][i] !== 0) {
                    swapRows(i, k, data);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new Error('Метод Гаусса не применим');
            }
        }

        for (let j = i + 1; j < data.size; j++) {
            let ratio = data.matrix[j][i] / data.matrix[i][i];
            for (let k = i; k < data.size; k++) {
                data.matrix[j][k] -= ratio * data.matrix[i][k];
            }
            data.rightValues[j] -= ratio * data.rightValues[i];
        }
    }
}
/** Вычисление определителя матрицы */
const determinant = (data) => {
    let det = 1;
    try {
        forward(data.matrix,data.rightValues);
        for (let i = 0; i < data.size; i++) {
            det *= data.matrix[i][i];
        }
        return (data.swap%2===0) ? det : -det ;
    } catch (error) {
        console.log(error.message);
        return null;
    }
}
/** Обратных ход метода Гаусса */
const back = (data) => {
    let solution = [];
    for (let i = data.size - 1; i >= 0; i--) {
        let sum = 0;
        for (let j = i + 1; j < data.size; j++) {
            sum += data.matrix[i][j] * solution[j];
        }
        solution[i] = (data.rightValues[i] - sum) / data.matrix[i][i];
    }
    return solution;
}
/** Вычисление веторов невязок */
const residuals = (data) => {
    const residuals = [];
    for (let i = 0; i < data.size; i++) {
        let res = -data.rightValues[i];
        for (let j = 0; j < data.size; j++) {
            res += data.matrix[i][j] * back(data)[j];
        }
        residuals.push(res);
    }
    return residuals;
}