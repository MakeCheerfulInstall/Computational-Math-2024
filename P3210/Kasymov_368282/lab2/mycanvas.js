
function DRAW(obj){
	canvas_events.draw_info = obj;
	setTimeout(()=>{
		var a = [];
		a=a.concat(obj.r);
		a=a.concat(obj.interval_a);
		a=a.concat(obj.interval_b);
		
		var k = [canvas_events.get_canvas()[0].height/2,canvas_events.get_canvas()[0].width/2];
		canvas_events.set_canvas_state({x:k[1],y:k[0],size:Math.min(k[0],k[1])/Math.max(...a.map(x=>Math.abs(x)+1))});
	},100);
}

function UNDRAW(obj) {
	canvas_events.draw_info = {alg:0};
	canvas_events.need_repaint();
}


var canvas_events={
	on_draw_background: [
		(_,canvas,state)=>{
			_.fillStyle = "#"+Math.floor(Math.random()*0xeff+0x100).toString(16);
			_.fillRect(0, 0, 2, 2);
			
			if (canvas_events.draw_info.alg) {
				_.strokeStyle = "#0003";
				_.font = "11px Arial";
				//_.lineWidth = 0.5;
				var msize = 1;
				while (msize*state.size*50<Math.max(canvas.height,canvas.width)) msize*=10;
				var tl = [Math.floor(state.x/state.size/msize), Math.floor(state.y/state.size/msize)];
				_.textAlign = "center";
				_.textBaseline = "middle";
				_.fillStyle = '#000';
				for (var i=0;i<100;i++) {
					_.beginPath();
					_.moveTo(0, i*msize*state.size+state.y-tl[1]*state.size*msize);
					_.lineTo(canvas.width, i*msize*state.size+state.y-tl[1]*state.size*msize, 1, 1 );
					_.stroke();
					
					if (i-tl[1])
						_.fillText(''+(i-tl[1])*msize, state.x+10, i*msize*state.size+state.y-tl[1]*state.size*msize);
				}
				for (var i=0;i<100;i++) {
					_.beginPath();
					_.moveTo(i*msize*state.size+state.x-tl[0]*state.size*msize, 0);
					_.lineTo(i*msize*state.size+state.x-tl[0]*state.size*msize, canvas.height, 1, 1 );
					_.stroke();
					
					if (i-tl[0])
						_.fillText(''+(i-tl[0])*msize, i*msize*state.size+state.x-tl[0]*state.size*msize, state.y+10);
				}
				
				//_.lineWidth = 1;
				
				_.strokeStyle = "#000";
				_.beginPath();
				_.moveTo(0, state.y);
				_.lineTo(canvas.width, state.y, 1, 1 );
				_.stroke();
				_.beginPath();
				_.moveTo(state.x, 0);
				_.lineTo(state.x, canvas.height, 1, 1 );
				_.stroke();
				
				if (canvas_events.draw_info.g) {
					_.strokeStyle = "#F00";
					canvas_events.draw_info.g.map((f)=>{
						var inline = false;
						for (var i=0;i<canvas.width;i++) {
							var fval = -f((i-state.x)/state.size);
							if (fval!=fval) fval= undefined;
							if (fval!==undefined)
								if (!inline) {
									inline=true;
									_.beginPath();
									_.moveTo(i, fval*state.size+state.y);
								} else
									_.lineTo(i, fval*state.size+state.y);
							if (i==canvas.width-1 || (inline && fval===undefined)) {
								inline = false;
								_.stroke();
							}
						}
					});
				}
				
				if (canvas_events.draw_info.f) {
					_.strokeStyle = "#F00";
					canvas_events.draw_info.f.map((f)=>{
						var inline = false;
						for (var i=0;i<canvas.height;i++) {
							var fval = f(-(i-state.y)/state.size);
							if (fval!=fval) fval= undefined;
							if (fval!==undefined)
								if (!inline) {
									inline=true;
									_.beginPath();
									_.moveTo(fval*state.size+state.x,i);
								} else
									_.lineTo(fval*state.size+state.x,i);
							if (i==canvas.height-1 || (inline && fval===undefined)) {
								inline = false;
								_.stroke();
							}
						}
					});
				}
				[canvas_events.draw_info.interval_a, canvas_events.draw_info.interval_b].map((x,i)=>{
					if (x) {
						_.strokeStyle = "#FF0"
						x.map(x=>{
							_.beginPath();
							_.moveTo(x*state.size+state.x+10*(0.5-i), state.y-10);
							_.lineTo(x*state.size+state.x, state.y-10);
							_.lineTo(x*state.size+state.x, state.y);
							_.lineTo(x*state.size+state.x, state.y+10);
							_.lineTo(x*state.size+state.x+10*(0.5-i), state.y+10);
							_.stroke();
						})
					}
				});
				
				if (canvas_events.draw_info.r) {
					_.fillStyle = "#333";
					canvas_events.draw_info.r.map(x=>{
						_.beginPath();
						_.arc(x*state.size+state.x, state.y, 3, 0, 2 * Math.PI, false);
						_.fill();
					});
				}
				
				if (canvas_events.draw_info.path) {
					_.font = "11px Arial";
					canvas_events.draw_info.path.map((p,i)=>{
						_.fillStyle = "#f00";
						_.beginPath();
						_.arc(p.x*state.size+state.x, -p.y*state.size+state.y, 3, 0, 2 * Math.PI, false);
						_.fill();
						
						if (i==canvas_events.draw_info.path.length-1)
							_.fillStyle = "#fff";
						else
							_.fillStyle = "#ccc";
						_.fillText(i+1 ,p.x*state.size+state.x, -p.y*state.size+state.y);
					});
				}
				
			}
		}
	],
	on_draw: [
		(_,canvas,state)=>{
			//_.fillStyle = "#"+Math.floor(Math.random()*0xeff+0x100).toString(16);
			//_.fillRect(0, 0, 1, 1);
		}
	],
	on_move: [],
	on_click: [],
	
	autopaint: false,
	need_repaint: ()=>{
		if (!canvas_events.autopaint)
			requestAnimationFrame(canvas_events.repaint); 
		canvas_events.autopaint = true;
	},
	get_canvas: ()=>{return [0, 0]},
	
	repaint: ()=>{},
	get_canvas_state: ()=>({x:0,y:0,size:1}),
	set_canvas_state: ()=>{},
	
	draw_info: {alg:0}
};

function onload_canvas() {
	const canvas = document.getElementById('canvas');
	const ctx = canvas.getContext('2d');
	canvas_events.get_canvas = ()=> {return [canvas, ctx];};
	ctx.imageSmoothingEnabled = false;


	let isDragging = false;
	let dragStartPosition = { x: 0, y: 0 };
	let currentTransformedCursor;

   canvas_events.repaint = ()=>{
		var state = canvas_events.get_canvas_state();
		ctx.save();
		ctx.setTransform(1,0,0,1,0,0);
		ctx.clearRect(0,0,canvas.width,canvas.height);
		canvas_events.on_draw_background.map(x=>x(ctx,canvas,state));
		ctx.restore();
		canvas_events.on_draw.map(x=>x(ctx,canvas,state));
		requestAnimationFrame(()=>{canvas_events.autopaint = false;});
	}

	function getTransformedPoint(x, y) {
	  const originalPoint = new DOMPoint(x, y);
	  return ctx.getTransform().invertSelf().transformPoint(originalPoint);
	}
	canvas_events.get_canvas_state = ()=>{
		const p = ctx.getTransform();
		return {x:p.e, y:p.f, size: p.a};
	}
	canvas_events.set_canvas_state = (obj)=>{
		ctx.setTransform(obj.size,0,0,obj.size,obj.x,obj.y);
		canvas_events.need_repaint();
	}
	function onMouseDown(event) {
		isDragging = true;
		dragStartPosition = getTransformedPoint(event.offsetX, event.offsetY);
	}

	function onMouseMove(event) {
		currentTransformedCursor = getTransformedPoint(event.offsetX, event.offsetY);
		
		if (isDragging) {
			ctx.translate(currentTransformedCursor.x - dragStartPosition.x, currentTransformedCursor.y - dragStartPosition.y);
			canvas_events.need_repaint();
		}
	}

	function onMouseUp() {
		if (isDragging) {
			isDragging = false;
			canvas_events.need_repaint();
		}
	}

	function onWheel(event) {
		let zoom = Math.pow(Math.E,-event.deltaY*Math.log(1.1)/100); //event.deltaY < 0 ? 1.1 : 0.9;
		ctx.translate(currentTransformedCursor.x, currentTransformedCursor.y);
		ctx.scale(zoom, zoom);
		ctx.translate(-currentTransformedCursor.x, -currentTransformedCursor.y);
		canvas_events.need_repaint();
	}

	canvas.addEventListener('mousedown', onMouseDown, {passive: false});
	canvas.addEventListener('mousemove', onMouseMove, {passive: false});
	canvas.addEventListener('mouseup', onMouseUp, {passive: false});
	canvas.addEventListener('mouseleave', onMouseUp, {passive: false});
	
	canvas.addEventListener('touchstart', (e)=>{
			const {x, y, width, height} = e.target.getBoundingClientRect();
			e.offsetX = (e.touches[0].clientX-x)/width*e.target.offsetWidth;
			e.offsetY = (e.touches[0].clientY-y)/height*e.target.offsetHeight;
			onMouseDown(e);
		}, {passive: false});
	canvas.addEventListener('touchmove', (e)=>{
			const {x, y, width, height} = e.target.getBoundingClientRect();
			e.offsetX = (e.touches[0].clientX-x)/width*e.target.offsetWidth;
			e.offsetY = (e.touches[0].clientY-y)/height*e.target.offsetHeight;
			onMouseMove(e);
		}, {passive: false});
	canvas.addEventListener('touchend', (e)=>{
			const {x, y, width, height} = e.target.getBoundingClientRect();
			e.offsetX = (e.changedTouches[0].clientX-x)/width*e.target.offsetWidth;
			e.offsetY = (e.changedTouches[0].clientY-y)/height*e.target.offsetHeight;
			onMouseUp(e);
		}, {passive: false});
	canvas.addEventListener('touchcancel', (e)=>{
			const {x, y, width, height} = e.target.getBoundingClientRect();
			e.offsetX = (e.changedTouches[0].clientX-x)/width*e.target.offsetWidth;
			e.offsetY = (e.changedTouches[0].clientY-y)/height*e.target.offsetHeight;
			onMouseUp(e);
		}, {passive: false});
	
	canvas.addEventListener('wheel', onWheel, {passive: false});
	canvas_events.need_repaint();
	ctx.textBaseline = "middle";
	ctx.textAlign = "center";
}

