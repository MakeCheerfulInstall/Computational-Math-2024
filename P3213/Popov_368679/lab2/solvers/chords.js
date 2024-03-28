import {checkDeltaMoreEpsilon, checkIsInsideInterval, handleOutEpsilonByIterationsCount} from "./Equations.js";

/** Метод хорд */
export class Chords {
    columnNames = ["a", "b", "x", "f(a)", "f(b)", "f(x)", "|x_i+1 - x_i|"];

    solve(data) {
        let table= {columnNames: this.columnNames, rows: []};
        const F = data.functionData.value;
        let start = data.start_x;
        let end = data.end_y;
        let xPrev = start;
        let iterations = 0;
        let xCurr, fCurr;
        while (++iterations < 20) {
            const fStart = F(start);
            const fEnd = F(end);
            xCurr = start - ((end - start) / (fEnd - fStart)) * fStart;
            fCurr = F(xCurr);
            table.rows.push([start, end, xCurr, fStart, fEnd, fCurr, Math.abs(xCurr - xPrev)]);

            if (!checkIsInsideInterval(xCurr, data) || !checkDeltaMoreEpsilon(start, end, xCurr, data)) {
                break;
            }

            if (fStart * fCurr < 0) {
                end = xCurr;
            } else {
                start = xCurr;
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
