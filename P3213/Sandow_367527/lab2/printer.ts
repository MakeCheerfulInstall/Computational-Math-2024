import { EquationInputData } from "./solvers/equation-solver";
import functionPlot, { FunctionPlotDatum } from "function-plot";
import { EquationSystemInputData, TaskType } from "./reader";

/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 1;


/** Данные для таблицы промежуточных результатов решения нелинейного уравнения */
export interface Table {
  columnNames: string[];
  rows?: number[][];
}

/** Результат решения нелинейного уравнения */
export interface EquationResult {
  x?: number;
  f?: number;
  iterationsCount?: number;
  table?: Table;
}

/** Результат решения системы нелинейных уравнений */
export interface EquationSystemResult {
  solution?: number[];
  iterationsCount?: number;
  valuesVectors?: number[][];
  discrepancy?: number[];
}

export function showResult(
  inputData: EquationInputData | EquationSystemInputData,
  type: TaskType,
  result: Required<EquationResult> | Required<EquationSystemResult>,
): void {
  renderPlot(inputData, type, result);

  switch (type) {
    case "equation":
      result = result as Required<EquationResult>;
      printX(result.x);
      printF(result.f);
      printIterationsCount(result.iterationsCount);
      printTable(result.table);
      break;
    case 'system':
      result = result as Required<EquationSystemResult>;
      printSystemSolution(result.solution);
      printSystemIterationsCount(result.iterationsCount);
      printSystemValuesVectors(result.valuesVectors);
      printSystemDiscrepancy(result.discrepancy);
      break;
  }

  unhideResultSection(type);
}

function renderPlot(
  inputData: EquationInputData | EquationSystemInputData,
  type: TaskType,
  result: EquationSystemResult | EquationInputData,
): void {
  let xDomain: number[] = [];
  let functionsData: FunctionPlotDatum[] = [];
  switch (type) {
    case 'equation':
      inputData = inputData as EquationInputData;
      result = result as EquationResult;
      xDomain = [inputData.start - SPACE_BEFORE_AND_AFTER_INTERVAL, inputData.end + SPACE_BEFORE_AND_AFTER_INTERVAL];
      console.log(result.solution);
      functionsData = [
        {
          fn: inputData.functionData.printableValue,
        },
        {
          points: [
            result.solution as number[],
          ],
          fnType: 'points',
          graphType: 'scatter',
          attr: {
            "stroke-width": `24px`,
          },
        },
      ];
      break;
    case 'system':
      inputData = inputData as EquationSystemInputData;
      result = result as EquationSystemResult;
      console.log(result.solution);
      xDomain = [-10, 10];
      functionsData = [
        {
          fn: inputData.system.f.printableValue,
          fnType: 'implicit',
        },
        {
          fn: inputData.system.g.printableValue,
          fnType: 'implicit',
        },
        {
          points: [
            result.solution as number[],
          ],
          fnType: 'points',
          graphType: 'scatter',
          attr: {
            "stroke-width": `12px`,
          },
        },
      ];
      break;
  }

  functionPlot({
    target: "#plot",
    width: 500,
    height: 500,
    xAxis: {
      domain: xDomain,
    },
    grid: true,
    data: functionsData,
  });
}

function printX(x: number) {
  document.getElementsByClassName('result__solution')[0].textContent = (
    x.toFixed(PRINT_PRECISION)
  );
}

function printF(f: number) {
  document.getElementsByClassName('result__function-value')[0].textContent = (
    f.toFixed(PRINT_PRECISION)
  );
}

function printIterationsCount(iterationsCount: number) {
  document.getElementsByClassName('result__iterations-count')[0].textContent = (
    iterationsCount.toString()
  );
}

function printTable(table: Table) {
  const tableElement = document.getElementsByClassName('result__table')[0];
  const tableHead = tableElement.getElementsByTagName('thead')[0];
  tableHead.innerHTML = '';
  for (const columnName of table.columnNames) {
    const th = document.createElement('th');
    th.textContent = columnName;
    tableHead.appendChild(th);
  }

  const tableBody = tableElement.getElementsByTagName('tbody')[0];
  tableBody.innerHTML = '';

  for (const row of (table.rows ?? [])) {
    const tr = document.createElement('tr');
    for (const cell of row) {
      const td = document.createElement('td');
      td.textContent = cell.toFixed(PRINT_PRECISION);
      tr.appendChild(td);
    }
    tableBody.appendChild(tr);
  }
}

function unhideResultSection(type: TaskType): void {
  document.getElementsByClassName('result')[0]?.classList.remove('hidden');

  switch (type) {
    case 'equation':
      document.getElementById('equation-result')?.classList.remove('hidden');
      document.getElementById('system-result')?.classList.add('hidden');
      break;
    case 'system':
      document.getElementById('equation-result')?.classList.add('hidden');
      document.getElementById('system-result')?.classList.remove('hidden');
      break;
  }
}

function printSystemSolution(solution: number[]): void {
  document.getElementsByClassName('result__system-solution')[0].textContent = (
    convertVectorToString(solution)
  );
}

function printSystemIterationsCount(iterationsCount: number): void {
  console.log(iterationsCount);
  document.getElementsByClassName('result__system-iterations-count')[0].textContent = (
    iterationsCount.toString()
  );
}

function printSystemDiscrepancy(discrepancy: number[]): void {
  document.getElementsByClassName('result__system-discrepancy')[0].textContent = (
    convertVectorToString(discrepancy)
  );
}

function printSystemValuesVectors(valuesVectors: number[][]): void {
  const tableElement = document.getElementsByClassName('result__system-value-vectors')[0];
  const tableHead = tableElement.getElementsByTagName('thead')[0];
  tableHead.innerHTML = '';
  for (const columnName of ['X', 'Y']) {
    const th = document.createElement('th');
    th.textContent = columnName;
    tableHead.appendChild(th);
  }

  const tableBody = tableElement.getElementsByTagName('tbody')[0];
  tableBody.innerHTML = '';

  for (const [x, y] of valuesVectors) {
    const tr = document.createElement('tr');
    for (const cell of [x, y]) {
      const td = document.createElement('td');
      td.textContent = cell.toFixed(PRINT_PRECISION);
      tr.appendChild(td);
    }
    tableBody.appendChild(tr);
  }
}

function convertVectorToString(vector: number[]): string {
  return `(${vector.map(x => x.toFixed(PRINT_PRECISION)).join(', ')})`;
}
