import {
    checkIsInsideInterval,
    checkDeltaMoreEpsilon,
    handleOutEpsilonByIterationsCount,
    checkInterval
} from "./Equations.js"
/** Метод Ньютона */
export class PolDel {
    columnNames = ["a_i", "b_i","x_i", "F(a_i)", "F(b_i)", "F(x_i)","|a_i - b_i|"];

    solve(data) {
        let table= {columnNames: this.columnNames, rows: []};
        const F = data.functionData.value;
        let a = data.start_x;
        let b = data.end_y;
        let iterations = 0;
        let xCurr = (a+b)/2;
        let fCurr = F(xCurr);
        while (++iterations < 20) {
            const fA = F(a);
            const fB = F(b);
            if(F(a)*F(xCurr) < 0){
                b = xCurr;
            }else{
                a = xCurr;
            }

            xCurr = (a+b)/2;
            fCurr = F(xCurr);

            table.rows.push([a, b,xCurr, fA, fB,fCurr,Math.abs(a - b)]);
            if (!checkIsInsideInterval(xCurr, data) || !checkInterval(a, b,xCurr , data)) {
                break;
            }
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