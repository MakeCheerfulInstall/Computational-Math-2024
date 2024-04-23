import {InputData} from './reader.js';

/** Максимальное допустимое N. */
export const MAX_N = 20;

/**
 * Результат решения СЛАУ.
 *
 * @property solution - вектор найденных неизвестных.
 * @property iterationsCount - количество итераций.
 * @property errorVectors - векторы погрешностей для каждой итерации.
 */
export interface Result {
  solution: number[];
  iterationsCount: number;
  errorVectors: number[][];
}

/**
 * Получить решение СЛАУ с подсчётом кол-ва итераций и погрешностей на каждой итерации.
 *
 * @throws {DiagonallyDominationUnobtainable} - если невозможно достичь диагонального преобладания.
 */
export function solveLinearEquationsSystem(data: InputData): Result {
  if (!obtainDDFrom(data)) {
    throw new DiagonallyDominationUnobtainable();
  }

  bringToNormalCoefficients(data);

  let lastVector = data.rightValues;
  let iterations = 0;
  let errorVectors: number[][] = [];
  let maxDelta = 1000000000;
  while (maxDelta > data.epsilon){
    errorVectors.push([]);
    let curVector = calcCurrentVector(lastVector, data);
    maxDelta = 0;
    for (let i = 0; i < data.size; i++) {
      const delta = Math.abs(lastVector[i] - curVector[i]);
      errorVectors[iterations].push(delta);
      maxDelta = Math.max(maxDelta, delta);
    }
    lastVector = curVector;
    iterations++;
  }

  return {
    solution: lastVector,
    iterationsCount: iterations,
    errorVectors: errorVectors
  };
}

/** Проверить достижимо ли диагональное преобладание матрицы и если да, то привести ее к такой форме */
function obtainDDFrom(input: InputData): boolean {
  let origMatrix = input.matrix;
  let sumAll = origMatrix.reduce((totalSum, row) => {
    return totalSum + row.reduce((rowSum, num) => rowSum + num, 0);
  }, 0);
  const allPermutations = generatePermutations(input.size);
  for (let comb of allPermutations) {
    if (isDiagonallyDominating(origMatrix, comb, sumAll)) {
      rearrangeMatrixToDDForm(input, comb);
      return true;
    }
  }
  return false;
}

/** Сгенерировать все перестановки чисел от 0 до n-1 */
function generatePermutations(n: number): number[][] {
  const permutations: number[][] = [];
  const nums: number[] = [];
  // Заполнение массива числами от 0 до n - 1
  for (let i = 0; i < n; i++) {
    nums.push(i);
  }
  // Рекурсивная функция для генерации перестановок
  function generate(nums: number[], index: number): void {
    if (index === nums.length) {
      permutations.push(nums.slice()); // Добавляем текущую перестановку в массив
      return;
    }
    for (let i = index; i < nums.length; i++) {
      // Меняем местами текущий элемент с элементом на позиции index
      [nums[index], nums[i]] = [nums[i], nums[index]];
      generate(nums, index + 1); // Рекурсивный вызов для следующей позиции
      // Возвращаем массив к исходному состоянию перед следующей итерацией
      [nums[index], nums[i]] = [nums[i], nums[index]];
    }
  }

  generate(nums, 0); // Начинаем генерацию с индекса 0
  return permutations;
}

/** Проверить диагональное преобладание матрицы. */
function isDiagonallyDominating(origMatrix: number[][], comb: number[], sumAll: number): boolean {
  let sum = 0;
  for (let i = 0; i < origMatrix.length; i++) {
    sum += origMatrix[i][comb[i]];
  }
  return sum * 2 >= sumAll;
}

/** Переставить строки в матрице и элементы в числах справа в нужном порядке. */
function rearrangeMatrixToDDForm(data: InputData, comb: number[]): void {
  let rearrangedMatrix: number[][] = [];
  let rearrangedValues: number[] = [];
  for (let i = 0; i < data.size; i++) {
    rearrangedMatrix.push([]);
    rearrangedValues.push(0);
  }

  for (let i = 0; i < data.size; i++) {
    const index = comb[i];
    rearrangedMatrix[index] = data.matrix[i];
    rearrangedValues[index] = data.rightValues[i]
  }
  data.matrix = rearrangedMatrix;
  data.rightValues = rearrangedValues;
}

/**  Разделить все числа в строке i (в т ч rightValue[i]) на диагональное число matrix[i][i]
 * Диагональные коэффициенты в итоге = 1 */
function bringToNormalCoefficients(data: InputData): void {
  for (let i = 0; i < data.size; i++) {
    const k = data.matrix[i][i];
    for (let j = 0; j < data.size; j++) {
      data.matrix[i][j] /= k;
    }
    data.rightValues[i] /= k;
  }

}

/** Заполнить значениями новый вектор на i-той итерации, опираясь на значения (i-1)-ой итерации и коэффициенты*/
function calcCurrentVector(lastVector: number[], input: InputData): number[]{
  let curVector: number[] = [];
  for (let i = 0; i < input.size; i++){
    let val = input.rightValues[i];
    for (let j = 0; j < input.size; j++){
      if (i != j){
        val -= input.matrix[i][j] * lastVector[j];
      }
    }
    curVector.push(val);
  }
  return curVector;
}

/** Ошибка о том, что невозможно достичь диагонального преобладания исходной матрицы. */
export class DiagonallyDominationUnobtainable extends Error {
  constructor() {
    super();
    this.name = 'DiagonallyDominationUnobtainable';
  }
}
