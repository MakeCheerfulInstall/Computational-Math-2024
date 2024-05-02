import { getReader } from './reader';
import "./style.css";
import { showResult } from "./printer";
import { getCubicFunction } from "./logic/functions/cubic";
import { getExponentialFunction } from "./logic/functions/exponential";
import { getLinearFunction } from "./logic/functions/linear";
import { getLogFunction } from "./logic/functions/logarithmic";
import { getPowerFunction } from "./logic/functions/power";
import { getSquareFunction } from "./logic/functions/square";
import { ApproximatedFunction } from "./logic/definitions";

const solverFunctions = [
  getCubicFunction,
  getExponentialFunction,
  getLinearFunction,
  getLogFunction,
  getPowerFunction,
  getSquareFunction,
];

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event: Event): void {
  event.preventDefault();
  const reader = getReader();

  reader.read()
    .then(input => {
      let bestApproximatedFunction: ApproximatedFunction | null = null;

      solverFunctions.forEach(solver => {
        const result = solver(input) as Required<ApproximatedFunction>;
        if (
          bestApproximatedFunction === null ||
          result?.standardDeviation < bestApproximatedFunction?.standardDeviation
        ) {
          bestApproximatedFunction = result;
        }
      });

      showResult(input, bestApproximatedFunction);
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

window.onload = () => {
  addHandlerForSolverButton();
};
