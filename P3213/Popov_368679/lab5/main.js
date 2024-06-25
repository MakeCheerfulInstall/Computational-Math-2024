import { Solver } from "./solver.js";
import { getReader } from "./reader.js";
import "./style.css";
import { showResult} from "./printer.js";
import functionPlot from "function-plot";

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event) {
  event.preventDefault();
  const reader = getReader();

  reader
    .read()
    .then((input) => {
      const solver = new Solver();
      const result = solver.getAllCalculations(input);
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

function addHandlerForInputTypeSelect() {
  const inputTypeButtons = document.getElementsByName("input-type-select");
  inputTypeButtons.forEach((button) => {
    button.addEventListener("change", (event) => {
      const checkedRadio = event.target ;
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
