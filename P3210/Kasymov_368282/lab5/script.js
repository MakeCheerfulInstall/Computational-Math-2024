window._ = (x) => {
    let s = math.evaluate(x).toString();
    if (s === 'true') return true;
    if (s === 'false') return false;
    return s;
}

const funcs = [
    ["-3*x^3 - x^2 + x + 3"],
    ["-2*x^3 - 4*x^2 + 8*x - 4"],
    ["-2*x^3 - 3^2 + x + 5"]
];

const methods = [
    [lagrSolve, "Лагранжа"],
    [newtonSepSolve, "Ньютона с разделенными разностями"],
    [newtonFinSolve, "Ньютона с конечными разностями"],
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

function handleMethodSel(val) {
    // remove points of prev methods
    calculator.getExpressions().forEach(e => {
        if (e.id.startsWith("method")) {
            calculator.removeExpression({ id: e.id })
        }
    })
    methods[val][0]()
}

function handleFuncSel(val) {
    calculator.getExpressions().forEach(e => {
        calculator.removeExpression({ id: e.id })
    })

    let funcLeftBound = prompt("enter the left bound")
    let funcRightBound = prompt("enter the right bound")
    let pointsN = prompt("enter the number of points")
    if (isNaN(+funcLeftBound) || isNaN(+funcRightBound) || isNaN(+pointsN)) {
        alert("wrong format!")
    }
    if (+funcLeftBound >= +funcRightBound || pointsN <= 1) {
        alert("wrong format!")
    }
    funcLeftBound = +funcLeftBound
    funcRightBound = +funcRightBound
    pointsN = +pointsN
    let func = funcs[val]

    let gap = (funcRightBound - funcLeftBound) / pointsN
    xs.length = 0
    ys.length = 0
    for (let i = funcLeftBound; i <= funcRightBound; i += gap) {
        xs.push(i)
        ys.push(_(nerdamer(func).evaluate({ x: i }).toString()))
    }


    // set func in desmos
    calculator.setExpression({ id: "func", latex: func });

    for (let i = 0; i < xs.length; i++) {
        calculator.setExpression({ id: 'funcPoint' + i, latex: `(${xs[i]}, ${ys[i]})`, color: Desmos.Colors.RED })
    }

    handleMethodSel(0)
    document.getElementById('point-textarea').value = xs.join(" ") + "\n" + ys.join(" ")
    console.log(xs, ys)
}

function handleFileSelect() {
    const input = document.getElementById('file-input');
    const file = input.files[0];
    const reader = new FileReader();
    reader.onload = function () {
        const lines = reader.result.split('\n');
        xs = lines[0].split(" ").map(v => v.trim())     
        ys = lines[1].split(" ").map(v => v.trim())
        if (xs.length !== ys.length) throw "xs.length !== ys.length"
        if (new Set(xs).size !== xs.length) {
            alert("Messing around is forbidden")
            return
        }   
        calculator.getExpressions().forEach(e => {
            calculator.removeExpression({ id: e.id })
        })
        for (let i = 0; i < xs.length; i++) {
            calculator.setExpression({ id: 'funcPoint' + i, latex: `(${xs[i]}, ${ys[i]})`, color: Desmos.Colors.RED })
        }
        handleMethodSel(0)
        document.getElementById('point-textarea').value = reader.result
    };
    reader.readAsText(file);
}

function handlePointInput() {
    const input = document.getElementById('point-textarea').value;
    const lines = input.split('\n');
    xs = lines[0].split(" ").map(v => v.trim())
    ys = lines[1].split(" ").map(v => v.trim())
    if (xs.length !== ys.length) return
    if (new Set(xs).size !== xs.length) {
        alert("Messing around is forbidden")
        return
    }
    console.log("working")
    calculator.getExpressions().forEach(e => {
        calculator.removeExpression({ id: e.id })
    })
    for (let i = 0; i < xs.length; i++) {
        calculator.setExpression({ id: 'funcPoint' + i, latex: `(${xs[i]}, ${ys[i]})`, color: Desmos.Colors.RED })
    }
    handleMethodSel(0)
}

let xs = []
let ys = []

// methods
const rotate_table = (table) => {
    let t = []
    for (let i = 0; i < xs.length; i++) {
        t[i] = [xs[i]].concat(new Array(xs.length - i).fill(0).map((v, j) => table[j][i]))
    }
    return t
}

const table_for_ravnoots = () => {
    let a = [...ys]
    let r = []

    for (let x_i = 0; x_i < xs.length; x_i++) {
        let a_temp = []
        r.push(a)
        for (let j = 0; j + 1 < a.length; j++) {
            console.log(j + x_i + 1, j)
            a_temp.push(_(`(${a[j + 1]} - ${a[j]})`))
        }
        a = [...a_temp]
    }
    return r
}

const table_for_neravnoots = () => {
    let a = [...ys]
    let r = []

    for (let x_i = 0; x_i < xs.length; x_i++) {
        let a_temp = []
        r.push(a)
        for (let j = 0; j + 1 < a.length; j++) {
            console.log(j + x_i + 1, j)
            a_temp.push(_(`(${a[j + 1]} - ${a[j]})/(${xs[j + x_i + 1]} - ${xs[j]})`))
        }
        a = [...a_temp]
    }
    return r
}

function neravnoots_solve(target_x) {
    let t = table_for_neravnoots()
    let ans_to_compute = "0"
    let all = new Array(xs.length).fill(0).map((_, i) => `(x - x_${i})`)
    for (let i = 0; i < t.length; i++) {
        let skobki = all.slice(0, i).join("*")
        ans_to_compute += " + " + `${t[i][0]}` + (skobki === "" ? "" : "*") + skobki
    }

    let x_val = Object.fromEntries(xs.map((v, i) => ["x_" + i, v]))
    x_val.x = target_x

    return _(nerdamer(ans_to_compute).evaluate(x_val).toString())
}

function ravnootst_solve_right(target_x) {
    let table = rotate_table(table_for_ravnoots())
    // вывод таблицы 
    console.log(table)

    let start_x_i = xs.length - 1 // xs.findIndex((v, i) => _("" + target_x + " < " + v))
    let diff = xs[1] - xs[0]
    let t = _(`(${target_x} - ${xs[start_x_i]}) / ${diff}`)
    let ans = `${table[start_x_i][table[start_x_i].length - 1]} + 
    (t*${table[start_x_i - 1][table[start_x_i - 1].length - 1]})`

    let all = new Array(xs.length).fill(0).map((_, i) => `(t + ${i})`)
    for (let i = start_x_i - 2, j = 3; i >= 0; i--, j++) {
        ans += `+ (t*${all.slice(1, j - 1).join("*")}*${table[i][table[i].length - 1]})/${j - 1}!`
    }

    return _(nerdamer(ans).evaluate({ t: t }).toString())
}

function ravnootst_solve_left(target_x) {
    let table = rotate_table(table_for_ravnoots())
    // вывод таблицы 
    console.log(table)

    let diff = xs[1] - xs[0] // h
    let all = new Array(xs.length).fill(0).map((_, i) => `(t - ${i})`) // скобочки t(𝑡 − 1)(𝑡 − 2)

    let start_x_i = xs.findLastIndex((v) => _(`${target_x} > ${v}`))
    let t = _(`(${target_x} - ${xs[start_x_i]}) / ${diff}`)
    let ans = `${table[start_x_i][1]} + (t*${table[start_x_i][2]})`

    for (let i = 3; i < table[start_x_i].length; i++) {
        ans += `+ (t*${all.slice(1, i - 1).join("*")}*${table[start_x_i][i]})/${i - 1}!`
    }

    return _(nerdamer(ans).evaluate({ t: t }).toString())
}

function lagr(target_x) {
    let fracs = new Array(xs.length) // [[числитель, знаменатель] [числитель, знаменатель] [числитель, знаменатель].....]

    const get_shared = (x_i) => {
        let all = new Array(xs.length).fill(0).map((_, i) => i)
        return all.slice(0, x_i).concat(all.slice(x_i + 1, xs.length)).map((v) => `(@ - x_${v})`)
    }

    for (let x_i = 0; x_i < xs.length; x_i++) {
        fracs[x_i] = [
            get_shared(x_i).join("*").replaceAll("@", "x"),
            get_shared(x_i).join("*").replaceAll("@", "x_" + x_i)
        ] // [числитель, знаменатель]
    }

    // просто построить многочлен 
    console.log(fracs)

    // вычислить значение
    let x_val = Object.fromEntries(xs.map((v, i) => ["x_" + i, v]))
    let ans = "0"
    for (let i in fracs) {
        let x_val_temp = { ...x_val }
        x_val_temp.x = target_x
        let sum_part = nerdamer(`(${fracs[i][0]} * (${ys[i]})) / (${fracs[i][1]})`).evaluate(x_val_temp)
        ans = _(`${ans} + ${sum_part}`)
    }

    return ans
}

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

function lagrSolve() {
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

function newtonSepSolve() {
    let xsOut = ["x"]
    let ysOut = ["y"]
    document.getElementById('table-cont').innerHTML = arrayToTable(rotate_table(table_for_neravnoots()))
    for (let i = 0; i < xs.length - 1; i++) {
        let m = _(`(${xs[i]} + ${xs[i + 1]}) / 2`)
        let sol = neravnoots_solve(m)
        xsOut.push(m); ysOut.push(sol)
        calculator.setExpression({ id: 'methodPoint' + i, latex: `(${m}, ${sol})`, color: Desmos.Colors.GREEN })
    }
    document.getElementById('result-points').innerHTML = arrayToTable([xsOut, ysOut])
}

function newtonFinSolve() {
    let xsOut = ["x"]
    let ysOut = ["y"]
    document.getElementById('table-cont').innerHTML = arrayToTable(rotate_table(table_for_ravnoots()))
    for (let i = 0; i < Math.floor(xs.length / 2); i++) {
        let m = _(`(${xs[i]} + ${xs[i + 1]}) / 2`)
        let sol = ravnootst_solve_right(m)
        xsOut.push(m); ysOut.push(sol)
        calculator.setExpression({ id: 'methodPoint' + i, latex: `(${m}, ${sol})`, color: Desmos.Colors.BLACK })
    }
    for (let i = Math.floor(xs.length / 2); i < xs.length - 1; i++) {
        let m = _(`(${xs[i]} + ${xs[i + 1]}) / 2`)
        let sol = ravnootst_solve_left(m)
        xsOut.push(m); ysOut.push(sol)
        calculator.setExpression({ id: 'methodPoint' + i, latex: `(${m}, ${sol})`, color: Desmos.Colors.BLACK })
    }

    document.getElementById('result-points').innerHTML = arrayToTable([xsOut, ysOut])
}