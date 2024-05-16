import { builtinEquations } from "./logic/equations";
import { Solver } from "./logic/Solver";
import { getReader } from "./reader";
import "./style.css";
import { showResult } from "./printer";

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event: Event): void {
  event.preventDefault();
  const reader = getReader();

  reader
    .read()
    .then((input) => {
      const result = Solver.solve(input);
      showResult(input, result);
    })
    .catch((e: Error) => {
      if (e.name === "ReadError") {
        alert(e.message);
        return;
      }
      throw e;
    });
}

function addHandlerForSolverButton(): void {
  const solveButton = document.getElementById("solve-button");
  solveButton?.addEventListener("click", onSolveClicked);
}

function showEquations(): void {
  const equationsContainer = document.getElementById(
    "equations-container"
  ) as HTMLDivElement;
  equationsContainer.innerHTML = "";

  for (const i in builtinEquations) {
    const equation = builtinEquations[i];
    equationsContainer.innerHTML += `
    <label for="equation${i}">
      <input type="radio" name="equation" id="equation${i}" value="${i}">
      <span class="equation">
        ${equation.printableYDerivative}
      </span>
    </label>
    `;
  }
}

window.onload = () => {
  showEquations();
  addHandlerForSolverButton();
};
