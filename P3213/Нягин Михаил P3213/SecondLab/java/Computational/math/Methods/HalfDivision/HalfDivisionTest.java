package Computational.math.Methods.HalfDivision;

import org.junit.Test;

public class HalfDivisionTest {

    @Test
    public void firstTestOfSecondFunction(){
        //fixme на первом не пашет почему-то
        HalfDivision hd = new HalfDivision(-2.5,-2,0.01f,1);
        hd.solve();
    }
    @Test
    public void secondTestOfFirstFunction(){

    }
}
