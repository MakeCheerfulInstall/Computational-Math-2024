import {checkIsInsideInterval, checkDeltaMoreEpsilon, handleOutEpsilonByIterationsCount} from "./Equations.js"
/** Метод Ньютона */
export class Newton {
    columnNames = ["x_i", "f(x_i)", "f'(x_i)", "x_i+1", "|x_i+1 - x_i|"];

    solve(data) {
        let table= {columnNames: this.columnNames, rows: []};
        const F = data.functionData.value;
        const FDer = data.functionData.derivative;
        let xPrev = data.end_y;
        let iterations = 0;
        let xCurr, fCurr;
        while (++iterations < 20) {
            const fPrev = F(xPrev);
            const fDerPrev = FDer(xPrev);
            xCurr = xPrev - fPrev / fDerPrev;
            fCurr= F(xCurr);
            console.log(xPrev, fPrev, fDerPrev, xCurr, Math.abs(xCurr - xPrev))
            table.rows.push([xPrev, fPrev, fDerPrev, xCurr, Math.abs(xCurr - xPrev)]);

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
}