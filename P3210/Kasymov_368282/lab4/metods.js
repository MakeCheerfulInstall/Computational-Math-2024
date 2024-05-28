

metods.equation.push({
	"name":"Метод половинного деления",
	"eps_req": true,
	"interval_req": true,
	"calculate":
		(f, arg)=>{
			let a = arg['a'];
			let b = arg['b'];
			let eps = arg['eps'];
			let path = [];
			
			if (f(a) * f(b) >= 0) return "Уравнение не удовлетворяет условию метода половинного деления.";
			
			let c = a;
			var iter = 0;
			while (1) {
				iter++;
				c = (a + b) / 2;
				path.push({x: c, y: f(c)});
				
				if ((b - a) < eps) {
					break;
				} else if (f(c) === 0.0) {
					break;
				} else if (f(c) * f(a) < 0) {
					b = c;
				} else {
					a = c;
				}
			}
			
			return {path: path, text: 'Корень: ('+math.floor(c,math.floor(4-math.log10(eps))) + ', '+math.floor(f(c),math.floor(4-math.log10(eps)))+'). Количество итераций: '+iter};
		}
});

metods.equation.push({
	"name":"Метод секущих",
	"eps_req": true,
	"interval_req": true,
	"calculate":
		(f, arg, df) => {
			let a = arg['a'];
			let b = arg['b'];
			let eps = arg['eps'];
			let path = [];
			
			let x_prev = 0;
			let x_curr = a;
			let x_next = b;
			let tmp;
			
			path.push({x: x_curr, y: f(x_curr)});
			
			var iter = 0;
			do
			{
				iter++;
				x_prev = x_curr;
				x_curr = x_next;
				x_next = x_curr - f(x_curr) * (x_prev - x_curr) / (f(x_prev) - f(x_curr));
				
				path.push({x: x_next, y: f(x_next)});
			} while (Math.abs(x_next - x_curr) > eps);
			
			return {path: path, text: 'Корень: ('+math.floor(x_next,math.floor(4-math.log10(eps))) + ', '+math.floor(f(x_next),math.floor(4-math.log10(eps)))+'). Количество итераций: '+iter};
		}
});


metods.equation.push({
	"name": "Метод простой итерации",
	"eps_req": true,
	"x0_req": true,
	"calculate": (f, arg, df) => {
		let x0 = arg['x0'];
		let eps = arg['eps'];
		let path = [];
		
		let lambda = 1/df(x0);
		
		const g = (x) => {
		  return x-lambda*f(x);
		};

		let x_next = x0;
		path.push({x: x_next, y: f(x_next)});
		let iter = 0;
		let x_current;
		do {
			  x_current = x_next;
			  x_next = g(x_current);
			  if (Math.abs((x_next-g(x_current+eps/10))*eps/10)>=1) return 'не выполнено условие сходимости!';
			  path.push({x: x_next, y: f(x_next)});
			  iter++;
		} while (Math.abs(x_next - x_current) >= eps);

		return {
			  path: path,
			  text: 'Корень: (' + math.floor(x_next,math.floor(4-math.log10(eps))) + ', '+math.floor(f(x_next),math.floor(4-math.log10(eps))) + '). Количество итераций: ' + iter
		};
	}
});

metods.system.push({
  "name": "Метод ньютона",
  "eps_req": true,
  "x0_req": true,
  "calculate": (f, g, jacobian, arg) => {
    let x = arg['x0'];
    let y = arg['y0'];
    let eps = 0.01;
    let path = [];
    
    let iter = 0;
    path.push({x:x, y:y});

    let delta;
    do {
      let fg = [[f(x, y)],[g(x,y)]];
      
      let jac = jacobian(x, y);

      try{delta = math.multiply(math.inv(jac), math.multiply(-1, fg));} catch{break;}
	  delta = [delta[0][0], delta[1][0]];
      x -= delta[0];
      y -= delta[1];

      path.push({x:x, y:y});
      iter++;
    } while (math.norm(delta) >= eps);
	
    return {
      path: path,
      text: 'Корень: (' + math.floor(x,math.floor(4-math.log10(eps))) + ', '+math.floor(y,math.floor(4-math.log10(eps))) + '). Количество итераций: ' + iter
    };
  }
});