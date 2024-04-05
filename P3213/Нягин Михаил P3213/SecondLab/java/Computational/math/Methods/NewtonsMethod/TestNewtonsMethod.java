package Computational.math.Methods.NewtonsMethod;

import Computational.math.Functions.SystemFunctions;
import org.junit.Test;

public class TestNewtonsMethod {
    @Test
    public void tr(){
        SystemFunctions sf = new SystemFunctions(1);

        Double[] ans = sf.getCalculatedChosenFunction(1,2);
        System.out.println("x=" + ans[0] +" y=" + ans[1]);
    }
    @Test
    public void firstTest(){
        NewtonsMethod nm = new NewtonsMethod(1,2,0.01f,1);
        nm.solve();
    }
}
