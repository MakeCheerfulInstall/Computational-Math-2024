window._ = (x) => {
    let s = math.evaluate(x).toString();
    if (s === 'true') return true;
    if (s === 'false') return false;
    return s;
}

const eqs = [
    ["-3*x^3 - x^2 + x + 3"],
    ["-2*x^3 - 4*x^2 + 8*x - 4"],
    ["-2*x^3 - 3^2 + x + 5"],
    ["x^2"]];

const methods = [
    [sqr_l, "Левых прямоугольников"],
    [sqr_r, "Правых прямоугольников"],
    [sqr_c, "Средних прямоугольников"],
    [trap, "Трапеций"],
    [simp, "Симпсона"],
]

let calculator = null;

const onLoad = () => {
    const eqEl = document.getElementById('eq_sel')
    const methodsSel = document.getElementById('sel_method')
    for (let i = 0; i < methods.length; i++){
        methodsSel.innerHTML += `<option value="${i}">${methods[i][1]}</option>`
    }
    for (let i = 0; i < eqs.length; i++) {
        eqEl.innerHTML += `<option value="${i}">${eqs[i]}</option>`
    }

    var elt = document.getElementById('calculator');
    calculator = Desmos.GraphingCalculator(elt, {"expressionsCollapsed": true});
}

const methodFuncMap = new Map([["squares_l", sqr_l], ["squares_r", sqr_r], ["squares_c", sqr_c], ["trap", trap], ["simp", simp]])

// helpers
const invalidFomat = () => alert("invalid fomat!")

const getPrecision = () => {
    let pres = +window.prompt("enter precision", 0.1)
    if (isNaN(pres)) {
        invalidFomat()
        return null
    }
    return pres
}

const getBounds = () => {
    let boundsStr = window.prompt("enter boundaries like this: -1 4", 0)
    bounds = boundsStr.split(" ").map(i => +i)
    if (bounds.length !== 2 || bounds.some(v => isNaN(v)) || bounds[0] > bounds[1]) {
        invalidFomat()
        return null
    }
    return bounds
}

const getSuite = () => {
    bounds = [];
    pres = []
    while (1) {
        bounds = getBounds()
        pres = getPrecision()
        if (bounds !== null && pres !== null) break;
    }
    return [bounds, pres]
}

const evalExpr = (eqI, x) => {
    return nerdamer(eqs[eqI][0]).evaluate({x: x}).toString()
}

const getRealAns = (eqI, bounds) => {
    var integ = nerdamer(`integrate(${eqs[eqI]}, x)`);
    return _(`${nerdamer(integ).evaluate({x: bounds[1]})} - ${nerdamer(integ).evaluate({x: bounds[0]})}`)
}

const changeEq = (idstr) => {
    debugger
    calculator.setExpression({ id: 'm', latex: eqs[+idstr][0]});
}

// method imp-s
function sqr_l(eqInd) {
    console.log("left squares method starting...");
    [bounds, pres] = getSuite()
    let numOfIntervals = 4

    const sqr_l_with_iters = (interN) => {
        let s = "0", step = _(`(${bounds[1]} - ${bounds[0]}) / ${interN}`)
        for (pointer = bounds[0]; _(`${pointer} < ${bounds[1]}`); pointer = _(`${pointer}+${step}`)) {
            s = _(`${s} + ${step}*${evalExpr(eqInd, pointer)}`)
        }
        return s
    }

    while (numOfIntervals < 100) {
        let i_h = sqr_l_with_iters(numOfIntervals)
        let i_h_half = sqr_l_with_iters(numOfIntervals / 2)
        let err = _(`abs(${i_h} - ${i_h_half}) / (2^2 - 1)`)
        if (_(`${err} < ${pres}`)) return [i_h, numOfIntervals];
        numOfIntervals+=2
    }
    return [sqr_l_with_iters(numOfIntervals), numOfIntervals];
}

function sqr_r(eqInd) {
    console.log("right squares method starting...");
    [bounds, pres] = getSuite()
    let numOfIntervals = 4

    const sqr_r_with_iters = (interN) => {
        let s = "0", step = _(`(${bounds[1]} - ${bounds[0]}) / ${interN}`)
        for (pointer = _(`${bounds[0]}+${step}`); _(`${pointer} <= ${bounds[1]}`); pointer = _(`${pointer}+${step}`)) {
            s = _(`${s} + ${step}*${evalExpr(eqInd, pointer)}`)
        }
        return s
    }

    while (numOfIntervals < 100) {
        let i_h = sqr_r_with_iters(numOfIntervals)
        let i_h_half = sqr_r_with_iters(numOfIntervals / 2)
        let err = _(`abs(${i_h} - ${i_h_half}) / (2^2 - 1)`)
        if (_(`${err} < ${pres}`)) return [i_h, numOfIntervals];
        numOfIntervals+=2
    }
    return [sqr_l_with_iters(numOfIntervals), numOfIntervals];
}

function sqr_c(eqInd) {
    console.log("center squares method starting...");
    [bounds, pres] = getSuite()
    let numOfIntervals = 4

    const sqr_c_with_iters = (interN) => {
        let s = "0", step = _(`(${bounds[1]} - ${bounds[0]}) / ${interN}`)
        for (pointer = _(`(${bounds[0]}+${step})/2`); _(`${pointer} <= ${bounds[1]}`); pointer = _(`${pointer}+${step}`)) {
            s = _(`${s} + ${step}*${evalExpr(eqInd, pointer)}`)
        }
        return s
    }

    while (numOfIntervals < 100) {
        let i_h = sqr_c_with_iters(numOfIntervals)
        let i_h_half = sqr_c_with_iters(numOfIntervals / 2)
        let err = _(`abs(${i_h} - ${i_h_half}) / (2^2 - 1)`)
        if (_(`${err} < ${pres}`)) return [i_h, numOfIntervals];
        numOfIntervals+=2
    }
    return [sqr_c_with_iters(numOfIntervals), numOfIntervals];
}

function trap(eqInd) {
    console.log("trap method starting...");
    [bounds, pres] = getSuite()
    let numOfIntervals = 4

    const trap_with_iters = (interN) => {
        let s = "0", step = _(`(${bounds[1]} - ${bounds[0]}) / ${interN}`)
        for (let i = 1; i < interN; i++) {
            let x = _(`${bounds[0]} + ${i} * ${step}`)
            s = _(`${s} + ${evalExpr(eqInd, x)}`)
        }
        let end = _(`${bounds[0]} + ${interN} * ${step}`)
        s = _(`(${s}*2 + ${evalExpr(eqInd, bounds[0])} + ${evalExpr(eqInd, end)})*${step}*0.5`)

        return s
    }

    while (numOfIntervals < 100) {
        let i_h = trap_with_iters(numOfIntervals)
        let i_h_half = trap_with_iters(numOfIntervals / 2)
        let err = _(`abs(${i_h} - ${i_h_half}) / (2^2 - 1)`)
        if (_(`${err} < ${pres}`)) return [i_h, numOfIntervals];
        numOfIntervals+=2
    }
    return [trap_with_iters(numOfIntervals), numOfIntervals];
}

function simp(eqInd) {
    console.log("simp method starting...");
    [bounds, pres] = getSuite()
    let numOfIntervals = 4

    const simp_with_iters = (interN) => {
        let step = _(`(${bounds[1]} - ${bounds[0]}) / ${interN}`)
        let fs = "0", ss = "0"
        for (let i = 1; i < interN; i++) {
            let y = evalExpr(eqInd, _(`${bounds[0]} + ${i}*${step}`))
            if(i % 2 == 0) {
                ss = _(`${ss} + ${y}`)
            } else {
                fs = _(`${fs} + ${y}`)
            }
        }
        let yn = evalExpr(eqInd, _(`${bounds[0]} + ${step} * ${interN}`))
        let s = _(`${step}/3 * (${evalExpr(eqInd, bounds[0])} + 4*(${fs}) + 2*(${ss}) + ${yn})`)
        return s
    }

    while (numOfIntervals < 100) {
        debugger
        let i_h = simp_with_iters(numOfIntervals)
        let i_h_half = simp_with_iters(numOfIntervals / 2)
        let err = _(`abs(${i_h} - ${i_h_half}) / (2^4 - 1)`)
        if (_(`${err} < ${pres}`)) return [i_h, numOfIntervals];
        numOfIntervals += 2
    }
    return [simp_with_iters(numOfIntervals), numOfIntervals];
}

// main funcs 
function handleCalcBtn() {
    const eq_ind = document.getElementById("eq_sel").selectedIndex
    const method_ind = document.getElementById("sel_method").selectedIndex
    let sol_iter = methods[+method_ind][0](+eq_ind)
    alert(`intervals took: ${sol_iter[1]}\nanswer: ${sol_iter[0]}`)
}