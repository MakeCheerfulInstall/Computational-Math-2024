package Computational.math.Methods.SecantMethod;

import Computational.math.Functions.Functions;
import Computational.math.Methods.AbstractMethod;
import org.junit.Test;

import java.util.function.Function;

public class SecantMethodTest {
        @Test
        public void firstTest(){
            AbstractMethod si = new SecantMethod(-2,-1,0.01f,1);
            si.solve();
        }
        @Test
        public void secondTest(){
            AbstractMethod si = new SecantMethod(1,2,0.000001f,2);
            si.solve();
        }
        @Test
        public void thirdTest(){
            AbstractMethod si = new SecantMethod(-4,-3,0.01f,3);
            si.solve();
        }
        @Test
    public void calculator(){
            Function<Double,Double> function = new Functions(1).getFunction();
            System.out.println(function.apply(-1.667));
        }

}
