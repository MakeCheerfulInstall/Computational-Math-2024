package Computational.math.LinearSystem;

import org.junit.Test;

public class SimpleIterationLinSystemTest
{
    @Test
    public void test(){
        Double[][] system = new Double[][]{
                {2d,4d},{-6d,1d}
        };
        Double[] answers = new Double[] {-1d,1d};
        SimpleIterationLinSystem simpleIterationLinSystem = new SimpleIterationLinSystem(system,answers,0.0000000000000000000000000001);
        Double[] ans = simpleIterationLinSystem.solve();
        System.out.println("x="+ ans[0]+"\ny=" +ans[1]);
    }
}
