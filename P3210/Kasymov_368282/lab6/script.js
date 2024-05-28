window._ = (x) => {
    let s = math.evaluate(x).toString();
    if (s === 'true') return true;
    if (s === 'false') return false;
    return s;
}

const funcs = [
    ["y+(1+x)*y^2"],
    ["y+(1+x)*y^2"],
    ["y+(1+x)*y^2"]
];

const diffs = [
    "y=-(e^x)/(x*e^x+C)",
    "y=-(e^x)/(x*e^x+C)",
    "y=-(e^x)/(x*e^x+C)"
]

const methods = [
    [ailerSolve, "Эйлера"],
    [betterAilerSolve, "Улучшенный метод Эйлера"],
    [adamseSolve, "Адамса"],
]

let calculator = null

function onLoad() {
    let sel = document.getElementById("func-sel")
    funcs.forEach((f, i) => {
        sel.innerHTML += `
            <option value="${i}">${f}</option>`
    })

    let msel = document.getElementById("method-sel")
    methods.forEach((m, i) => {
        msel.innerHTML += `
            <option value="${i}">${m[1]}</option>`
    })

    var elt = document.getElementById('calculator');
    calculator = Desmos.GraphingCalculator(elt, { expressionsCollapsed: true });

    handleFuncSel(0)
}

let precision = 0.1
let xStart = 0
let yStart = 0

function handleMethodSel(val) {
    // remove points of prev methods
    calculator.getExpressions().forEach(e => {
        if (e.id.startsWith("method")) {
            calculator.removeExpression({ id: e.id })
        }
    })
    methods[val][0]()
}

let xs = []
let f = null

function handleFuncSel(val) {
    calculator.getExpressions().forEach(e => {
        calculator.removeExpression({ id: e.id })
    })

    let funcLeftBound = prompt("enter the left bound")
    let funcRightBound = prompt("enter the right bound")
    let pointsN = prompt("enter the number of points")
    precision = prompt("enter the precision")
    if (isNaN(+funcLeftBound) || isNaN(+funcRightBound) || isNaN(+pointsN) || isNaN(+precision)) {
        alert("wrong format!")
    }
    if (+funcLeftBound >= +funcRightBound || pointsN <= 1 || precision < 0) {
        alert("wrong format!")
    }

    xStart = prompt("enter initial value for X")
    yStart = prompt("enter initial value for Y")
    if (isNaN(+xStart) || isNaN(+yStart) || xStart < funcLeftBound || xStart > funcRightBound) {
        alert("wrong format!")
    }

    funcLeftBound = +funcLeftBound
    funcRightBound = +funcRightBound
    pointsN = +pointsN
    let func = funcs[val]
    f = (x, y) => _(nerdamer(func).evaluate({ x: x, y: y }).toString())

    let gap = (funcRightBound - funcLeftBound) / pointsN
    xs.length = 0

    for (let i = funcLeftBound; i <= funcRightBound; i += gap) {
        xs.push(i)
    }

    let diffur = diffs[val].replaceAll("C", 0)
    // set func in desmos
    calculator.setExpression({ id: "func", latex: diffur });

    handleMethodSel(0)
    console.log(xs)
}

// methods
function arrayToTable(data) {
    let table = '<table border="1">';
    for (let i = 0; i < data.length; i++) {
        table += '<tr>';
        for (let j = 0; j < data[0].length; j++) {
            table += '<td>' + (data[i][j] || "") + '</td>';
        }
        table += '</tr>';
    }
    table += '</table>';
    return table;
}

const calcAiler = (xi, y, h, ysol) => {
    if (xi >= xs.length) return
    let fxy = f(xs[xi], y)
    ysol.push([xs[xi], y, fxy])
    calcAiler(xi + 1, _(`${y} + ${h} * ${fxy}`), h, ysol)
}

const calcBetterAiler = (xi, y, h, ysol) => {
    let fxy = f(xs[xi], y)
    ysol.push([xs[xi], y, fxy])
    if (xi + 1 >= xs.length) return
    let fullFxy = f(xs[xi + 1], _(`${y} + ${h} * ${fxy}`))
    calcBetterAiler(xi + 1, _(`${y} + (${h}/2) * (${fxy} + ${fullFxy})`), h, ysol)
}

function ailerSolve() {
    let methodOut = []
    calcAiler(+xStart, yStart, xs[1] - xs[0], methodOut)
    methodOut.forEach((v, i) => {
        calculator.setExpression({ id: 'methodPoint' + i, latex: `(${v[0]}, ${v[1]})`, color: Desmos.Colors.BLUE })
    })
    methodOut.unshift(["x_i", "y_i", "f(x_i, y_i)"])
    document.getElementById('result-points').innerHTML = arrayToTable(methodOut)
}

function betterAilerSolve() {
    let methodOut = []
    calcBetterAiler(+xStart, yStart, xs[1] - xs[0], methodOut)
    methodOut.forEach((v, i) => {
        calculator.setExpression({ id: 'methodPoint' + i, latex: `(${v[0]}, ${v[1]})`, color: Desmos.Colors.BLUE })
    })
    methodOut.unshift(["x_i", "y_i", "f(x_i, y_i)"])
    document.getElementById('result-points').innerHTML = arrayToTable(methodOut)
}

function adamseSolve() {
    let xsOut = ["x"]
    let ysOut = ["y"]
    document.getElementById('table-cont').innerHTML = ""
    for (let i = 0; i < xs.length - 1; i++) {
        let m = _(`(${xs[i]} + ${xs[i + 1]}) / 2`)
        let sol = lagr(m)
        xsOut.push(m); ysOut.push(sol)
        calculator.setExpression({ id: 'methodPoint' + i, latex: `(${m}, ${sol})`, color: Desmos.Colors.BLUE })
    }
    document.getElementById('result-points').innerHTML = arrayToTable([xsOut, ysOut])
}