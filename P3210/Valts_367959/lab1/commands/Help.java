package lab1.commands;


import lab1.IContext;

public class Help implements ICommand {
    @Override
    public void execute(IContext context, String[] args) {
        context.print("" +
                " solve_matrix            Read a linear equation system as a matrix. \n" +
                "           -f    [path]  Read matrix from file.                     \n" +
                "                         Example: solve_matrix -f in.txt            \n" +
                "           -s            Read with determinate size. M[s][s+1]      \n" +
                "           -r            Solve Random generated matrix              \n" +
                "                         Example: solve_matrix -s 4                 \n" +
                "                         size bigger than 0 and smaller 20          \n" +
                " exit                    terminate the application.                 \n" +
                " help                    Print the commands list.                   \n"
        );
    }
}
