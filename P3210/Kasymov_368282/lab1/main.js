math.config({
    number: 'BigNumber',
    precision: 500,
    epsilon: 1e-60
});

window._ = (x) => {
    let s = math.evaluate(x).toString();
    if (s === 'true') return true;
    if (s === 'false') return false;
    return s;
}
// проверяет, что матрица квадратная
const isMatrixWithBValid = (matrixWithB) => {
    if (matrixWithB.flat(1).some(n => isNaN(n) || n === "")) return false;
    return matrixWithB.every(r => matrixWithB.length === r.length - 1)
}

const calculate = (matrixWithB, maxNumOfIterations, precision) => {
    let preMatrix = [...matrixWithB].map(r => r.slice(0, r.length - 1))

// ширина и высота матрицы без b
    const width = preMatrix[0].length;
    const height = preMatrix.length;


    // проверяет, что норма C матрицы < 1
    const validateCMatrix = (CMatrix) => {
        for (let row of CMatrix) {
            let sum = row.slice(0, row.length - 1).reduce((acc, el) => _(`${math.abs(acc)} + ${math.abs(el)}`), 0)
            console.log(sum)
            if (_(`${sum} >= 1`)) return false;
        }
        return true;
    }

    // возвращает массив индексов строчек матрицы так,
    // чтобы условие преобладания диагональных элементов выполнялось. см презу
    const formReadyMatrix = (preMatrix) => {
        let diagDom = {};
        let validArrangement = null
        let strictValidSets = new Array(width).fill(0).map(e => new Set())
        const findArrangement = (diagInd, takenRowsSet, takenRowsArr) => {
            if (diagInd === width && takenRowsArr.some((val, ind) => strictValidSets[ind].has(val))) {
                validArrangement = [...takenRowsArr];
                return true
            }
            let someValid = false;
            if (!diagDom[diagInd]) return false;
            for (let rowInd of diagDom[diagInd]) {
                if (takenRowsSet.has(rowInd)) continue;
                takenRowsSet.add(rowInd)
                takenRowsArr.push(rowInd)
                someValid ||= findArrangement(diagInd + 1, takenRowsSet, takenRowsArr);
                takenRowsSet.delete(rowInd)
                takenRowsArr.pop();
            }
            return someValid;
        }
        for (let i = 0; i < width; i++) {
            for (let rowInd in preMatrix) {
                let diagSum = preMatrix[rowInd].reduce((acc, cur, ind) => i === ind ? acc : _(`${cur} + ${acc}`), 0);
                if (_(`${math.abs(diagSum)} <= ${math.abs(preMatrix[rowInd][i])}`)) {
                    diagDom[i] = (diagDom[i] || []).concat([rowInd]);
                }
                if (_(`${math.abs(diagSum)} < ${math.abs(preMatrix[rowInd][i])}`)) {
                    strictValidSets[i].add(rowInd)
                }
            }
        }
        // takenRowsSet нужен для того, чтобы определять какие строчки матрицы мы уже взяли
        // takenRowsArr для хранения порядка строчек, так как set не хранит порядок добавления
        findArrangement(0, new Set(), [])
        return validArrangement;
    }

    let mat = formReadyMatrix(preMatrix)
    if (!mat) {
        mat = []
        for (let i = 0; i < height; i++) mat.push(i)
        console.log("not enough diagonal dominance")
        alert("not enough diagonal dominance")
    }
    matrixWithB = mat.map(ind => matrixWithB[ind]);
    let CMatrix = new Array(height).fill(0).map(e => new Array(width).fill(0))
    for (let i = 0; i < width; i++) {
        for (let j = 0; j < width + 1; j++) {
            if (i === j) {
                CMatrix[i][j] = 0;
                continue
            }
            CMatrix[i][j] = _(`${j !== width ? -1 : 1}*${matrixWithB[i][j]} / ${matrixWithB[i][i]}`)
        }
    }
    if (!validateCMatrix(CMatrix)) {
        alert("norm of C matrix is not valid");
    }
    let currX = CMatrix.map(row => row[row.length - 1])
    let reached_iters = 0;
    for (let i = 0; i < maxNumOfIterations; i++) {
        reached_iters = i;
        let prevX = [...currX]
        currX = CMatrix.map(coffs => coffs.reduce((acc, el, ind) => _(`${acc} + ${(prevX[ind] || 1)}*${el}`), 0))
        let xGap = _(`max(${currX.map((x, ind) => math.abs(_(`${x} - ${prevX[ind]}`))).join(",")})`)
        if (_(`${xGap} < ${precision}`)) break;
    }
    console.log(currX)
    return [currX, reached_iters+1]
}
