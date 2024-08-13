package tests;

import Computational.Math.Methods.TrapezoidMethod;
import org.junit.Test;
import org.netirc.library.jtables.exception.MalformedTableException;

public class TrapezoidTest {
    @Test
    public void testFromLecture() throws MalformedTableException {
        TrapezoidMethod tr = new TrapezoidMethod();
        System.out.println(tr.solve(
                x->x*x,
                2d,4d,8,true
        ));
    }
}
