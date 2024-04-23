import { Result } from './solver.js';

/** Количество выводимых знаков после запятой. */
const PRECISION = 4;

/** Вывести все результаты решения. */
export function printResult(result: Result): void {
  printSolution(result.solution);
  printIterations(result.iterationsCount);
  printErrors(result.errorVectors);  
  unhideResultSection();
}

function unhideResultSection(): void {
  const resultSection = document.querySelector('.result') as HTMLDivElement;
  resultSection.style.display = 'block';
}

function printSolution(solution: number[]): void {
  const result = convertVectorToString(solution);
  const resultElement = document.querySelector('.result__solution') as HTMLParagraphElement;
  resultElement.innerText = result;
}

function printIterations(iterationsCount: number): void {
  const result = iterationsCount.toString();
  const resultElement = document.querySelector('.result__iterations-count') as HTMLParagraphElement;
  resultElement.innerText = result;
}

function printErrors(errorVectors: number[][]): void {
  const tableBodyElement = document.querySelector('.result__error-vectors-table tbody') as HTMLElement;
  tableBodyElement.innerHTML = '';
  for (let k = 1; k <= errorVectors.length; k++) {
    const vectorAsString = convertVectorToString(errorVectors[k - 1]);
    const row = document.createElement('tr');
    const cellForK = document.createElement('td');
    cellForK.innerText = k.toString();
    const cellForVector = document.createElement('td');
    cellForVector.innerText = vectorAsString;
    row.appendChild(cellForK);
    row.appendChild(cellForVector);
    tableBodyElement.appendChild(row);
  }
}

function convertVectorToString(vector: number[]): string {
  return `(${vector.map(x => x.toFixed(PRECISION)).join(', ')})`;
}
