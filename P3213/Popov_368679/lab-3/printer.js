/** Количество выводимых знаков после запятой. */
const PRINT_PRECISION = 4;
const SPACE_BEFORE_AND_AFTER_INTERVAL = 1;

export function showResult(result){
    printValue(result.value);
    printPartitions(result.partitionsNumber);
    unhideResultSection();
}

function printValue(value) {
    document.getElementsByClassName('result__value')[0].textContent = (
        value.toFixed(PRINT_PRECISION)
    );
}

function printPartitions(partitions) {
    document.getElementsByClassName('result__partitions')[0].textContent = (
        partitions.toString()
    );
}

function convertVectorToString(vector) {
    return `(${vector.map(x => x.toFixed(PRINT_PRECISION)).join(', ')})`;
}

function unhideResultSection() {
    document.getElementsByClassName('result')[0]?.classList.remove('hidden');
}
