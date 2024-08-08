package Computational.math.Methods.SimpleIteration;

import Computational.math.Methods.AbstractMethod;
import org.junit.Test;

public class TestSimpleIteration {
    //todo проверить условия сходимости и тд  и тп
    @Test
    public void firstTest(){
            AbstractMethod si = new SimpleIteration(-2,-1,0.01f,1);
            si.solve();
    }
    @Test
    public void secondTest(){
        AbstractMethod si = new SimpleIteration(1,2,0.000001f,2);
        si.solve();
    }
    @Test
    public void thirdTest(){
        AbstractMethod si = new SimpleIteration(-4,-3,0.01f,3);
        si.solve();
    }
}
