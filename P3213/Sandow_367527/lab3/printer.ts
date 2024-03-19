/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 1;


/** Результат решения нелинейного уравнения */
export interface EquationResult {
  value?: number; // значение интеграла
  partitionsNumber?: number; // число разбиения интервала
}

export function showResult(
  result: Required<EquationResult>
): void {
  printValue(result.value);
  printPartitions(result.partitionsNumber);
  unhideResultSection();
}

function printValue(value: number): void {
  document.getElementsByClassName('result__value')[0].textContent = (
    value.toFixed(PRINT_PRECISION)
  );
}

function printPartitions(partitions: number) {
  document.getElementsByClassName('result__partitions')[0].textContent = (
    partitions.toString()
  );
}

function convertVectorToString(vector: number[]): string {
  return `(${vector.map(x => x.toFixed(PRINT_PRECISION)).join(', ')})`;
}

function unhideResultSection(): void {
  document.getElementsByClassName('result')[0]?.classList.remove('hidden');
}
