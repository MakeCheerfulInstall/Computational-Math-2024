function solve_system_of_equations(A, B) {
    const n = A.length;
    const AM = A.map((x,i)=>x.concat([B[i]]));
    for (let i = 0; i < n; i++) {
        const mr = i + AM.slice(i).reduce((acc, r, ri) => Math.abs(r[i]) > Math.abs(AM[acc + i][i]) ? ri : acc, 0);
        [AM[i], AM[mr]] = [AM[mr], AM[i]];
        for (let k = i + 1; k < n; k++) {
            const f = AM[k][i] / AM[i][i];
            AM[k] = AM[k].map((e, j) => e - f * AM[i][j]);
        }
    }
    const s = [];
    for (let i = n - 1; i >= 0; i--) {
        let sum = AM[i][n];
        for (let j = i + 1; j < n; j++) {
            sum -= AM[i][j] * s[n - 1 - j];
        }
        s.push(sum / AM[i][i]);
    }
    return s.reverse();
}
function n_pow_aprox(xy,n) {
    var a=new Array(n).fill(0).map((x,i)=>new Array(n).fill(0).map((y,j)=>xy.x.map(y=>y**(i+j)).reduce((x,y)=>x+y)));
    var b=new Array(n).fill(0).map((x,i)=>xy.x.map((y,j)=>xy.y[j]*y**i).reduce((x,y)=>x+y));
    var solve = solve_system_of_equations(a,b)
    var ans = {};
    ans.fun  = (x)=>solve.map((a,i)=>a*x**i).reduce((x,y)=>x+y);
    ans.solve  = solve;
    return ans;
}
function exp_aprox(xy) {
    var solve = n_pow_aprox({x:xy.x, y:xy.y.map(x=>Math.log(x))},2).solve;
    solve[0] = Math.exp(solve[0])
    var ans = {};
    ans.fun  = (x)=>solve[0]*Math.exp(solve[1]*x);
    ans.solve  = solve;
    return ans;
}
function log_aprox(xy) {
    var solve = n_pow_aprox({x:xy.x.map(x=>Math.log(x)), y:xy.y},2).solve;
    [solve[0],solve[1]] = [solve[1],solve[0]];
    var ans = {};
    ans.fun  = (x)=>solve[0]*Math.log(x)+solve[1];
    ans.solve  = solve;
    return ans;
}
function power_aprox(xy) {
    var solve = n_pow_aprox({x:xy.x.map(x=>Math.log(x)), y:xy.y.map(x=>Math.log(x))},2).solve;
    solve[0] = Math.exp(solve[0])
    var ans = {};
    ans.fun  = (x)=>solve[0]*Math.pow(x,solve[1]);
    ans.solve  = solve;
    return ans;
}

function pearson_correlation_coefficient(xy) {
	const { x, y } = xy;
	const n = x.length;
	const avg_x = x.reduce((x, y) => x + y, 0)/n;
	const avg_y = y.reduce((x, y) => x + y, 0)/n;
	
	const sum_xy = x.map((xi, i) => (xi-avg_x)*(y[i]-avg_y)).reduce((x, y) => x + y, 0);
	const sum_x_squared = x.map(xi => (xi-avg_x)**2).reduce((x, y) => x + y, 0);
	const sum_y_squared = y.map(yi => (yi-avg_y)**2).reduce((x, y) => x + y, 0);
	const numerator = sum_xy;
	const denominator = Math.sqrt(sum_x_squared * sum_y_squared);
	const correlation = numerator / denominator;
	return correlation;
}

function aprox_S(xy, f) {
	return xy.x.map((x,i)=>(f(x)-xy.y[i])**2).reduce((x,y)=>x+y);
}
function aprox_D(xy, f) {
	return Math.sqrt(aprox_S(xy, f)/xy.x.length);
}
function aprox_R2(xy, f) {
	var avg = xy.x.map(x=>f(x)).reduce((x, y) => x + y)/xy.x.length;
	return 1-aprox_S(xy, f)/aprox_S(xy, ()=>avg);
}

function __html_gen(xy) {
	var html = '';
	html += 'Коэффициент корреляции Пирсона: '+pearson_correlation_coefficient(xy);
	var table_info = [
		{
			text: '𝝋 = 𝒂𝒙 + 𝒃',
			aprox: n_pow_aprox(xy, 2)
		},
		{
			text: '𝝋 = 𝒂𝒙<sup>2</sup> + 𝒃𝒙 + 𝒄𝒙',
			aprox: n_pow_aprox(xy, 3)
		},
		{
			text: '𝝋 = 𝒂𝒙<sup>3</sup> + 𝒃𝒙<sup>2</sup> + 𝒄𝒙 + 𝒅',
			aprox: n_pow_aprox(xy, 4)
		},
		{
			text: '𝝋 = 𝒂𝒙<sup>𝒃</sup>',
			aprox: power_aprox(xy)
		},
		{
			text: '𝝋 = 𝒂𝒆<sup>𝒃𝒙</sup>',
			aprox: exp_aprox(xy)
		},
		{
			text: '𝝋 = 𝒂𝒍𝒏𝒙 + 𝒃',
			aprox: log_aprox(xy)
		}
	];
	var щ = (x)=>math.floor(x,4);
	html += '<table>';
	html += '<tr>';
    html += '  <th>Вид функции</th>';
    html += '  <th>𝒂</th>';
    html += '  <th>𝒃</th>';
    html += '  <th>𝒄</th>';
    html += '  <th>𝒅</th>';
    html += '  <th></th>';
	xy.x.map((x,i)=>html += '  <th>'+(i+1)+'</th>')
    html += '  <th>Мера отклонения S</th>';
    html += '  <th>Среднеквадратичное отклонение 𝜹</th>';
    html += '  <th>Мера отклонения 𝑅<sup>2</sup></th>';
	html += '</tr>';
	html += '<tr>';
    html += '  <td colspan="5"></td>';
    html += '  <td>X</td>';
	xy.x.map((x,i)=>html += '  <td>'+x+'</td>');
    html += '  <td colspan="3">-</td>';
	html += '</tr>';
	html += '<tr>';
    html += '  <td colspan="5"></td>';
    html += '  <td>Y</td>';
	xy.y.map((x,i)=>html += '  <td>'+x+'</td>');
    html += '  <td colspan="3">-</td>';
	html += '</tr>';
	function t(x){
		if (x>0.95) return 'хорошо';
		if (x>0.75) return 'адекватно';
		if (x>0.5) return 'слабо';
		return 'недостаточно';
	}
	best = 0;
	table_info.map((apr,colori)=>{
		html += '<tr>';
		html += '  <td rowspan="2" style="color: '+["#99ff40","#99ff40","#99ff40","#99ff40","#99ff40","#99ff40","#99ff40","#99ff40"][colori]+'">'+apr.text+'</td>';
		var solve = colori<3?apr.aprox.solve.map(x=>x).reverse():apr.aprox.solve;
		html += '  <td rowspan="2">'+щ(solve[0])+'</td>';
		html += '  <td rowspan="2">'+щ(solve[1])+'</td>';
		html += '  <td rowspan="2">'+(solve[2]==undefined?'-':щ(solve[2]))+'</td>';
		html += '  <td rowspan="2">'+(solve[3]==undefined?'-':щ(solve[3]))+'</td>';
		html += '  <td>𝝋(x)</td>';
		xy.x.map((x,i)=>html += '  <td>'+щ(apr.aprox.fun(x))+'</td>');
		html += '  <td rowspan="2">'+щ(aprox_S(xy, apr.aprox.fun))+'</td>';
		if (aprox_D(xy, table_info[best].aprox.fun)>aprox_D(xy, apr.aprox.fun)) best = colori;
		html += '  <td rowspan="2">'+щ(aprox_D(xy, apr.aprox.fun))+'</td>';
		html += '  <td rowspan="2">'+щ(aprox_R2(xy, apr.aprox.fun)) +'<br>'+ t(aprox_R2(xy, apr.aprox.fun))+'</td>';
		html += '</tr>';
		html += '<tr>';
		html += '  <td>𝛆<sub>i</sub></td>';
		xy.x.map((x,i)=>html += '  <td>'+щ(apr.aprox.fun(x)-xy.y[i])+'</td>');
		html += '</tr>';
	});
	html += '</table>';
	html += 'Лучшая: '+table_info[best].text;
	return html;
}