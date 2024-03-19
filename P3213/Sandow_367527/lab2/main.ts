import { EquationSystemInputData, getReader } from './reader';
import { EquationInputData, EquationSolver, EquationSolvingMethod, functions } from "./solvers/equation-solver";
import "./style.css";
import { Chords } from "./solvers/chords";
import { Newton } from "./solvers/newton";
import { SimpleIterations } from "./solvers/simpleIterations";
import { EquationResult, EquationSystemResult, showResult } from "./printer";
import { EquationSystemSolver, systems } from "./solvers/equation-system-solver";
import { NewtonForSystems } from "./solvers/newtonForSystems";
import { SimpleIterationsForSystems } from "./solvers/simpleIterationsForSystems";

/** Обработать нажатие кнопки "Решить". */
function onSolveClicked(event: Event): void {
  event.preventDefault();
  const reader = getReader();

  reader.read()
    .then(({ data, type }) => {
      let result: Required<EquationResult> | Required<EquationSystemResult>;
      switch (type) {
        case "equation":
          result = passDataToEquationSolver(data as EquationInputData) as Required<EquationResult>;
          break;
        case "system":
          result = passDataToSystemSolver(data as EquationSystemInputData) as Required<EquationSystemResult>;
          break;
      }

      showResult(data, type, result);
    })
    .catch((e: Error) => {
      if (e.name === 'ReadError') {
        alert(e.message);
        return;
      }
      throw e;
    });
}

function passDataToEquationSolver(inputData: EquationInputData): EquationResult {
  let solver!: EquationSolver;
  switch (inputData.method) {
    case 'chords':
      solver = new Chords();
      break;
    case 'newton':
      solver = new Newton();
      break;
    case 'simple-iterations':
      solver = new SimpleIterations();
      break;
  }

  return solver.solve(inputData);
}

function passDataToSystemSolver(inputData: EquationSystemInputData): EquationSystemResult {
  let solver: EquationSystemSolver;
  switch (inputData.method) {
    case 'system-newton':
      solver = new NewtonForSystems();
      break;
    case 'system-simple-iterations':
      solver = new SimpleIterationsForSystems();
      break;
  }

  return solver.solve(inputData);
}

function addHandlerForSolverButton(): void {
  const solveButton = document.getElementById('solve-button');
  solveButton?.addEventListener('click', onSolveClicked);
}

function addHandlerForTaskTypeRadios(): void {
  const taskTypeButton = document.getElementsByName('task-type');
  taskTypeButton.forEach((button) => {
    button.addEventListener('change', (event) => {
      const checkedRadio = event.target as HTMLInputElement;
      switch (checkedRadio.value) {
        case 'equation':
          document.getElementById('equation-fields')?.classList.remove('hidden');
          document.getElementById('system-fields')?.classList.add('hidden');
          break;
        case 'system':
          document.getElementById('equation-fields')?.classList.add('hidden');
          document.getElementById('system-fields')?.classList.remove('hidden');
          break;
      }
    });
  });
}

function loadEquations(): void {
  const equationsContainer = document.getElementById('equation-select-container') as HTMLDivElement;
  equationsContainer.innerHTML = '';

  for (const i in functions) {
    const equation = functions[i];
    equationsContainer.innerHTML += `
    <label for="equation${i}">
      <input type="radio" name="equation" id="equation${i}" value="${i}">
      <span>${convertPrintableValueToHTML(equation.printableValue)}</span>
    </label>
    `;
  }
}

function loadSystems(): void {
  const systemsContainer = (
    document.getElementById('system-select-container') as HTMLDivElement
  );
  systemsContainer.innerHTML = '';

  for (const i in systems) {
    const system = systems[i];
    systemsContainer.innerHTML += `
    <label for="system${i}">
      <input type="radio" name="system" id="system${i}" value="${i}">
      <span class="system">
        <span>{</span>
        <span>
          <div>${convertPrintableValueToHTML(system.f.printableValue)}</div>
          <div>${convertPrintableValueToHTML(system.g.printableValue)}</div>
        </span>
      </span>
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
  addHandlerForTaskTypeRadios();
  loadEquations();
  loadSystems();
};
