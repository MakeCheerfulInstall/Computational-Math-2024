import { getReader } from './reader.js';
import { solveLinearEquationsSystem } from './solver.js';
import { printResult } from './printer.js';

const RANDOM_ELEMENT_MAX_VALUE = 10;

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event: Event): void {
  event.preventDefault();
  const reader = getReader();
  
  reader.read()
    .then(inputData => {
      try {
        const result = solveLinearEquationsSystem(inputData);
        printResult(result);
      } catch (e) {
        if (e instanceof Error && e.name === 'DiagonallyDominationUnobtainable') {
          alert('Невозможно достичь диагонального преобладания исходной матрицы');
          return;
        }
        throw e;
      }
    })
    .catch((e: Error) => {
      if (e.name === 'ReadError') {
        alert(e.message);
        return;
      }
      throw e;
    });
}

function generateRandomMatrix(): void {
  const reader = getReader();
  try {
    const n = reader.readN();
    const matrixInput = document.querySelector('textarea[name="matrix"]') as HTMLTextAreaElement;
    matrixInput.value = '';
    for (let i = 0; i < n; i++) {
      const row: number[] = [];
      for (let j = 0; j < n + 1; j++) {
        const randomNumber = Math.ceil(Math.random() * RANDOM_ELEMENT_MAX_VALUE);
        row.push(randomNumber);
      }

      const stringRow = row.join(' ') + '\n';
      matrixInput.value += stringRow;
    }
  } catch (e) {
    if (e instanceof Error && e.name === 'DiagonallyDominationUnobtainable') {
      alert(e.message);
      return;
    }

    throw e;
  }
}

window.onload = () => {
  const solveButton = document.getElementById('solve-button');
  solveButton?.addEventListener('click', onSolveClicked);

  const randomMatrixButton = document.getElementById('random-matrix-button');
  randomMatrixButton?.addEventListener('click', e => {
    e.preventDefault();
    generateRandomMatrix();
  });
};
