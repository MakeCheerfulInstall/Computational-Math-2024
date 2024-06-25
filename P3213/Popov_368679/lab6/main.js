import { builtinEquations } from "./equations";
import { Solve } from "./solve";
import { getReader } from "./reader";
import "./style.css";
import { showResult } from "./printer";
import functionPlot from "function-plot";

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event) {
  event.preventDefault();
  const reader = getReader();

  reader
    .read()
    .then((input) => {
        let solver = new Solve();
      const result = solver.solve(input);
      showResult(input, result);
    })
    .catch((e) => {
      if (e.name === "ReadError") {
        alert(e.message);
        return;
      }
      throw e;
    });
}

function addHandlerForSolverButton() {
  const solveButton = document.getElementById("solve-button");
  solveButton?.addEventListener("click", onSolveClicked);
}

function showEquations() {
  const equationsContainer = document.getElementById(
    "equations-container"
  );
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
    functionPlot({
        target: "#plot",
        width: 500,
        height: 500,
        xAxis: {
            domain: [-10,10],
        },
        grid: true,
        data: []
    })
};
