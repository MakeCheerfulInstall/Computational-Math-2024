import {checkDeltaMoreEpsilon, checkIsInsideInterval, handleOutEpsilonByIterationsCount} from "./Equations.js";

/** Метод простых итераций */
export class ProstoIter {
    columnNames = ["x_i", "x_i+1", "f(x_i+1)", "|x_i+1 - x_i|"];

    solve(data) {
        let table = {columnNames: this.columnNames, rows: []};
        let iterations = 0;

        const FVal = data.functionData.value;
        const Phi = data.functionData.phi;
        let xPrev = this.findInitialApproximation(data);
        let xCurr, fCurr;
        while (++iterations < 20) {
            xCurr = Phi(xPrev);
            fCurr = FVal(xCurr);
            table.rows.push([xPrev, xCurr, fCurr, Math.abs(xCurr - xPrev)]);
            if (!checkIsInsideInterval(xCurr, data) || !checkDeltaMoreEpsilon(xPrev, xCurr, xCurr, data)) {
              break;
            }
            xPrev = xCurr;
        }
        handleOutEpsilonByIterationsCount(data, iterations, fCurr);
        return {
            x: xCurr,
            f: fCurr,
            iterationsCount: iterations,
            table: table
        };
    }

    findInitialApproximation(data) {
        if (this.checkSufficientCondition(data.start_x, data.functionData)) {
            return data.start_x;
        } else if (this.checkSufficientCondition(data.end_y, data.functionData)) {
            return data.end_y
        } else if (!this.checkSufficientCondition((data.end_y + data.start_x) / 2, data.functionData)) {
            alert("Не выполняется достаточное условие, но мы попробуем решить уравнение");
        }
        return (data.end_y + data.start_x) / 2;
    }

    checkSufficientCondition(x, F) {
        return Math.abs(F.phiDerivative(x)) < 1;
    }
}
