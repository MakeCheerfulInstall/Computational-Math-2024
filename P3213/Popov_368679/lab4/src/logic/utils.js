export function getDeviationMeasure(tableFunction, f){
  let s = 0;
  for (let i = 0; i < tableFunction.n; i++) {
    s += Math.pow(f(tableFunction.x[i]) - tableFunction.y[i], 2);
  }
  return s;
}

// Функция для вычисления стандартного отклонения
export function getStandardDeviation(tableFunction, f){
  return Math.sqrt(getDeviationMeasure(tableFunction, f) / tableFunction.n);
}

// Функция для вычисления надежности аппроксимации
export function getApproximationReliability(tableFunction, f){
  let sumYfi = 0;
  let sumFi2 = 0;
  let sumFi = 0;
  for (let i = 0; i < tableFunction.n; i++) {
    const fi = f(tableFunction.x[i]);
    sumYfi += Math.pow(tableFunction.y[i] - fi, 2);
    sumFi2 += Math.pow(fi, 2);
    sumFi += fi;
  }
  return 1 - (sumYfi / (sumFi2 - Math.pow(sumFi, 2) / tableFunction.n));
}

// Метод Крамера для решения систем линейных уравнений
export function kramerMethod(coefficients, answers) {
  const delta = determinant(coefficients);
  const deltaArr = [];
  for (let j = 0; j < coefficients.length; j++) {
    const newCoefficients = coefficients.map((row, i) => {
      return i === j ? answers : row.slice();
    });
    deltaArr.push(determinant(newCoefficients));
  }
  return deltaArr.map(deltaVal => deltaVal / delta);
}

// Получение минора матрицы
function getMatrixMinor(m, rowIndex, colIndex) {
  return m.filter((row, i) => i !== rowIndex)
    .map(row => row.filter((_, j) => j !== colIndex));
}

// Нахождение определителя матрицы
function determinant(matrix){
  if (matrix.length === 1) {
    return matrix[0][0];
  } else if (matrix.length === 2) {
    return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
  } else {
    let det = 0;
    for (let i = 0; i < matrix[0].length; i++) {
      det += (-1) ** i * matrix[0][i] * determinant(getMatrixMinor(matrix, 0, i));
    }
    return det;
  }
}

export function getCorrelationCoefficientPearson(tableFunction){
  let sum_xy = 0;
  let sum_x = 0;
  let sum_y = 0;
  const x_mean = tableFunction.x.reduce((acc, val) => acc + val, 0) / tableFunction.n;
  const y_mean = tableFunction.y.reduce((acc, val) => acc + val, 0) / tableFunction.n;
  for (let i = 0; i < tableFunction.n; i++) {
    sum_xy += (tableFunction.x[i] - x_mean) * (tableFunction.y[i] - y_mean);
    sum_x += (tableFunction.x[i] - x_mean) ** 2;
    sum_y += (tableFunction.y[i] - y_mean) ** 2;
  }
  return sum_xy / (Math.sqrt(sum_x * sum_y));
}
