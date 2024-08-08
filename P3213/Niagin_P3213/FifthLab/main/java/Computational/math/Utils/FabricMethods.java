package Computational.math.Utils;

import Computational.math.GraphicPart.MainComponents.MainFrame;
import Computational.math.Methods.*;

import java.util.ArrayList;
import java.util.List;


public class FabricMethods {
    private ArrayList<AbstractMethod> methodList;
    public FabricMethods(){
        methodList = new ArrayList<>(List.of(
                new LagrangeMethod(),
                new BesselInterpolation(),
                new NewtonMethodPolynomial(),
                new NewtonMethodInterpolation(),
                new StirlingInterpolation()
        ));
    }
    public void executeEverything(FunctionalTable functionalTable, double xCurr){
        //костыль, нужен исключительно для отрисовки ньютоновского прикола
        FunctionalTable newtonTable;
        boolean drawNewton = true;
        Double[] xNewton = functionalTable.getxArr();
        Double[] yNewton = new Double[xNewton.length];
        NewtonMethodInterpolation nm = new NewtonMethodInterpolation();
        for (int i = 0; i < functionalTable.dimension(); i++) {
            if(Double.isNaN((yNewton[i] = nm.apply(functionalTable, xNewton[i])))){
                xNewton = null; yNewton = null; drawNewton = false; break;
            }
        }
        newtonTable = new FunctionalTable(xNewton,yNewton);
        for(AbstractMethod m : methodList){
            var ans = m.apply(functionalTable, xCurr);
            if(!ans.isNaN())
                System.out.println(STR."\{m.getName()} дал ответ: \{ans}");
            else
                System.out.println("Полином Ньютона с конечными разностями дал ответ:  Узлы не являются равноотстоящими");
        }

        if (drawNewton){
            MainFrame.drawInterpolation("//todo придумать название",functionalTable,newtonTable,15,15);
        }else
            MainFrame.drawInterpolation("da",functionalTable,new FunctionalTable(new Double[]{functionalTable.getxArr()[0]},new Double[]{functionalTable.getyArr()[0]} ),15,15);



    }
}
