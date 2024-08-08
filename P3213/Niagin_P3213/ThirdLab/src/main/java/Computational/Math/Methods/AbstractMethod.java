package Computational.Math.Methods;


public abstract class AbstractMethod implements Solve {
    private final String methodName;

    public AbstractMethod(String methodName) {
        this.methodName = methodName;
    }


    public void printMethodName(){
        //156 это количество пунктиров в таблице
        for (int i = 0; i < 156-4-4-methodName.length(); i++) {
            //middle
            if(i==50){
                System.out.print("\t" + methodName + "\t");
            }
            System.out.print("*");
        }
        System.out.println();
    }
    public String getMethodName() {
        return methodName;
    }

}
