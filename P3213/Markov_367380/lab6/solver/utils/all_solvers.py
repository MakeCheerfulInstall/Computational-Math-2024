from P3213.Markov_367380.lab6.solver.solver import Solver
from P3213.Markov_367380.lab6.solver.adams_solver import AdamsSolver
from P3213.Markov_367380.lab6.solver.euler_solver import EulerSolver
from P3213.Markov_367380.lab6.solver.runge_kutt_solver import RungeKuttSolver

solvers: list[Solver] = [
    EulerSolver(),
    RungeKuttSolver(),
    AdamsSolver()
]
