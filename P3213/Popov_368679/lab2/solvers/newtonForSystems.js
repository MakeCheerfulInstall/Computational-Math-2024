/** Метод Ньютона для систем */
export class NewtonForSystems {
    solve(data){
        let valuesVectors = [];

        const F = data.functionData.f;
        const G = data.functionData.g;
        let xPrev = data.start_x;
        let yPrev = data.end_y;
        let iterations = 0;
        const offset = 0.000001;
        let jacobian = calculateJacobian(F, G, xPrev, yPrev);
        if (jacobian === 0) {
            xPrev -= offset;
            jacobian = calculateJacobian(F, G, xPrev, yPrev);
        }

        let xCurr = xPrev - getDeltaX(F, G, xPrev, yPrev) / jacobian;
        let yCurr = yPrev - getDeltaY(F, G, xPrev, yPrev) / jacobian;
        valuesVectors.push([xCurr, yCurr]);

        while (++iterations < 20) {
            xPrev = xCurr;
            yPrev = yCurr;

            jacobian = calculateJacobian(F, G, xPrev, yPrev);
            if (jacobian === 0) {
                xPrev -= offset;
                jacobian = calculateJacobian(F, G, xPrev, yPrev);
            }
            xCurr = xPrev - getDeltaX(F, G, xPrev, yPrev) / jacobian;
            yCurr = yPrev - getDeltaY(F, G, xPrev, yPrev) / jacobian;
            valuesVectors.push([xCurr, yCurr]);

            if (Math.abs(xCurr - xPrev) <= data.epsilon && Math.abs(yCurr - yPrev) <= data.epsilon) {
                break;
            }
        }
        return {
            solution: [xPrev, yPrev],
            iterationsCount: iterations,
            valuesVectors: valuesVectors,
            discrepancy: calcDiscrepancy(xPrev, yPrev, F, G)
        }
    }
}


function calculateJacobian(F, G, x, y) {
    const fDX = F.derivativeX(x, y);
    const fDY = F.derivativeY(x, y);
    const gDX = G.derivativeX(x, y);
    const gDY = G.derivativeY(x, y);
    return fDX * gDY - fDY * gDX;
}

function getDeltaX(F, G, x, y) {
    const f = F.value(x, y);
    const g = G.value(x, y);
    const fDY = F.derivativeY(x, y);
    const gDY = G.derivativeY(x, y);
    return f * gDY - fDY * g;
}

function getDeltaY(F, G, x, y) {
    const f = F.value(x, y);
    const g = G.value(x, y);
    const fDX = F.derivativeX(x, y);
    const gDX = G.derivativeX(x, y);
    return fDX * g - f * gDX;
}
function calcDiscrepancy(x, y, F, G) {
    return [Math.abs(F.value(x, y)), Math.abs(G.value(x, y))]
}
