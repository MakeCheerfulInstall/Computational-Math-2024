package lab2.gui;

import lab2.algebra.Function;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Math {
    public static final Function[][] EQUATIONS = new Function[][] {
            {
                new Function("sin(x)^2-x^2+1"), new Function("(sin(x)^2+1)^0.5"), new Function("x") // 1
            },
            {
                new Function("x^2-e^x-3x+2"), new Function("log(x^2-3x+2)"), new Function("x") // 2
            },
            {
                new Function("xe^{x^2}-sin(x)^2+3cos(x)+5"), new Function("(sin(x)^2-3cos(x)-5)/e^{x^2}"), new Function("x") // 3
            },
            {
                new Function("x^3-17"), new Function("17^(1/3)"), new Function("x") // 4
            }
    };

    public static final String[][] GRAPHS = new String[][] {
            {
                new String("y=sin(x)^2-x^2+1"), new String("y=(sin(x)^2+1)^0.5"), new String("y=x")
            },
            {
                new String("y=x^2-e^x-3x+2"), new String("y=log(x^2-3x+2)"), new String("y=x")
            },
            {
                new String("y=xe^{x^2}-sin(x)^2+3cos(x)+5"), new String("y=(sin(x)^2-3cos(x)-5)/e^{x^2}"), new String("y=x")
            },
            {
                new String("y=x^3-17"), new String("y=17^{1/3}"), new String("y=x")
            }
    };

    public static final String[] EQUATION = new String[] {
            new String("sin(x)^2 - x^2 + 1 = 0")
            ,
            new String("x^2 - e^x - 3x + 2 = 0")
            ,
            new String("xe^{x^2} - sin(x)^2 + 3cos(x) + 5 = 0")
            ,
            new String("x^3 - 17 = 0")
    };

    public static final net.objecthunter.exp4j.function.Function cubrt = new net.objecthunter.exp4j.function.Function("cbrt") {
        @Override
        public double apply(double... doubles) {
            return java.lang.Math.cbrt(doubles[0]);
        }
    };

    public static final Function[][] SYSTEMS = new Function[][] {
            {// 1
                new Function("0.1x^2+x+0.2y^2-0.3"), new Function("0.2x^2+y-0.1xy-0.7"),
                    new Function("((0.3-x-0.1x^2)/0.2)^0.5"), new Function("(0.2x^2-0.7)/(0.1x-1)"),
                    new Function("0.3-0.1x^2-0.2y^2"), new Function("0.7-0.2x^2-0.1xy"),
                    new Function("-0.2x"), new Function("-0.4y"), new Function("-0.4x-0.1y"), new Function("-0.1x")
            },
            {// 2
                new Function("-y^3+10x^2+5x-0.4"), new Function("0.8x^2 + 1 - 1.5y + 0.4xy"),
                    new Function(new ExpressionBuilder("cbrt(10x^2+5x-0.4)").function(cubrt).variables("x", "y").build()),
                    new Function("(0.8x^2+1)/(1.5-0.4x)"),
                    new Function("(-y^3+10x^2-0.4)/(-5)"), new Function("(0.8x^2+1+0.4xy)/1.5"),
                    new Function("-4x"), new Function("0.6y^2"), new Function("(16/15)*x+(4/15)*y"), new Function("(4/15)*x")
            }
    };

    public static final String[][] GRAPH = new String[][] {
            {
                new String("y=((0.3-x-0.1x^2)/0.2)^0.5"), new String("y=(0.2x^2-0.7)/(0.1x-1)")
            },
            {
                new String("y=(10x^2+5x-0.4)^(1/3)"), new String("(0.8x^2+1)/(1.5-0.4x)")
            }
    };

    public static final String[][] SYSTEM = new String[][] {
            {
                new String("0.1x^2 + x + 0.2y^2 - 0.3 = 0"), new String("0.2x^2 + y - 0.1xy - 0.7 = 0")
            },
            {
                new String("-y^3 + 10x^2 + 5x - 0.4 = 0"), new String("0.8x^2 + 1 - 1.5y + 0.4xy = 0")
            }
    };
}
