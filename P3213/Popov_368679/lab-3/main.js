import { getReader } from './reader.js';
import { functions } from "./solvers/funcs.js";
import { showResult } from "./printer.js";
import { EquationSolver } from "./solvers/solver.js";

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event) {
    event.preventDefault();
    const reader = getReader();

    reader.read()
        .then(inputData => {
            const solver = new EquationSolver();
            console.log(inputData.functionData)
            const result = solver.solve(inputData);
            if (result === null) {
                return;
            }

            showResult(result);
        })
        .catch((e) => {
            if (e.name === 'ReadError') {
                alert(e.message);
                return;
            }
            throw e;
        });
}

function addHandlerForSolverButton() {
    const solveButton = document.getElementById('solve-button');
    solveButton?.addEventListener('click', onSolveClicked);
}

function loadFunctions() {
    const functionsContainer = document.getElementById('function-select-container');
    functionsContainer.innerHTML = '';

    for (const i in functions) {
        const f = functions[i];
        functionsContainer.innerHTML += `
    <label for="function${i}">
      <input type="radio" name="function" id="function${i}" value="${i}">
      <span>${convertPrintableValueToHTML(f.printableValue)}</span>
    </label>
    `;
    }
}

function convertPrintableValueToHTML(printableValue) {
    return printableValue
        .replace(/\^(\d+)/g, '<sup>$1</sup>')
        .replace(/x/g, '<u>x</u>');
}

window.onload = () => {
    addHandlerForSolverButton();
    loadFunctions();
};
