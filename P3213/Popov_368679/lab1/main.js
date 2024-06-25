import { getReader } from './reader.js';
import { solveLinearEquationsSystem } from './solver.js';
import { printResult } from './printer.js';

const RANDOM_ELEMENT_MAX_VALUE = 10;
const RANDOM_DIAGONAL_ELEMENT_INCREMENT = 30;

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event) {
    event.preventDefault();
    const reader = getReader();

    reader.read()
        .then(inputData => {
            try {
                const result = solveLinearEquationsSystem(inputData);
                printResult(result);
            } catch (e) {
                alert(e.message)
                console.log(e.message)
            }
        })
        .catch((e) => {
            if (e.name === 'ReadError') {
                alert(e.message);
                return;
            }
            throw e;
        });
}

function generateRandomMatrix() {
    const reader = getReader();
    try {
        const n = reader.readN();
        const matrixInput = document.querySelector('textarea[name="matrix"]');
        matrixInput.value = '';
        for (let i = 0; i < n; i++) {
            const row = [];
            for (let j = 0; j < n + 1; j++) {
                let randomNumber = Math.ceil(Math.random() * RANDOM_ELEMENT_MAX_VALUE);

                if (i === j) {
                    randomNumber += RANDOM_DIAGONAL_ELEMENT_INCREMENT;
                }

                row.push(randomNumber);
            }

            const stringRow = row.join(' ') + '\n';
            matrixInput.value += stringRow;
        }
    } catch (e) {
        if (e instanceof Error && e.name === 'ReadError') {
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