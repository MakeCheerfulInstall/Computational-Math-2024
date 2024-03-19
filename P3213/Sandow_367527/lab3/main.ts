import { getReader } from './reader';
import { functions } from "./solvers/definitions";
import "./style.css";
import { EquationResult, showResult } from "./printer";
import { EquationSolver } from "./solvers/solver";

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event: Event): void {
  event.preventDefault();
  const reader = getReader();

  reader.read()
    .then(input => {
      const solver = new EquationSolver();
      const result = solver.solve(input);
      if (result === null) {
        return;
      }
      
      showResult(result as Required<EquationResult>);
    })
    .catch((e: Error) => {
      if (e.name === 'ReadError') {
        alert(e.message);
        return;
      }
      throw e;
    });
}

function addHandlerForSolverButton(): void {
  const solveButton = document.getElementById('solve-button');
  solveButton?.addEventListener('click', onSolveClicked);
}

function loadFunctions(): void {
  const functionsContainer = document.getElementById('function-select-container') as HTMLDivElement;
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

function convertPrintableValueToHTML(printableValue: string): string {
  return printableValue
    .replace(/\^(\d+)/g, '<sup>$1</sup>')
    .replace(/x/g, '<u>x</u>');
}

window.onload = () => {
  addHandlerForSolverButton();
  loadFunctions();
};
