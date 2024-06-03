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
      const solver = new Solver();
      const result = solver.getAllCalculations(input);
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

function addHandlerForInputTypeSelect(): void {
  const inputTypeButtons = document.getElementsByName("input-type-select");
  inputTypeButtons.forEach((button) => {
    button.addEventListener("change", (event) => {
      const checkedRadio = event.target as HTMLInputElement;
      switch (checkedRadio.value) {
        case "points":
          document
            .getElementById("points-controls")
            ?.classList.remove("hidden");
          document.getElementById("function-controls")?.classList.add("hidden");
          break;
        case "function":
          document.getElementById("points-controls")?.classList.add("hidden");
          document
            .getElementById("function-controls")
            ?.classList.remove("hidden");
          break;
      }
    });
  });
}

window.onload = () => {
  addHandlerForSolverButton();
  addHandlerForInputTypeSelect();
};
