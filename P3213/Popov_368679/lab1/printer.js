/** Количество выводимых знаков после запятой. */
const PRECISION = 4;

/** Вывести все результаты решения. */
export function printResult(result) {
    printMatrix(result.matrix)
    printSolution(result.solution);
    printDeterminate(result.determinate);
    printResiduals(result.residuals)
    unhideResultSection();
}

function unhideResultSection() {
    const resultSection = document.querySelector('.result');
    resultSection.style.display = 'block';
}

function printSolution(solution) {
    const result = convertVectorToString(solution);
    const resultElement = document.querySelector('.result__solution');
    resultElement.innerText = result;
}

function printDeterminate(determinate) {
    const result = determinate.toString();
    const resultElement = document.querySelector('.result__iterations-count');
    resultElement.innerText = result;
}

function printMatrix(matrix) {
    const tableBodyElement = document.querySelector('.result__error-vectors-table tbody');
    tableBodyElement.innerHTML = '';
    matrix.forEach(row => {
        const tr = document.createElement('tr');
        row.forEach(cell => {
            const td = document.createElement('td');
            td.textContent = cell.toFixed(PRECISION);
            tr.appendChild(td);
        })
        tableBodyElement.appendChild(tr)
    })
}

function convertVectorToString(vector) {
    return `(${vector.map(x => x.toFixed(PRECISION)).join(', ')})`;
}
function printResiduals(residuals) {
    const result = convertVectorToString(residuals);
    const resultElement = document.querySelector('.result__residuals');
    resultElement.innerText = result;
}