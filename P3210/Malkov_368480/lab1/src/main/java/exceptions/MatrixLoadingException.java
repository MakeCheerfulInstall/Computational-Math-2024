package exceptions;

public class MatrixLoadingException extends Exception{
    public MatrixLoadingException(){
        super("Can not parse entered matrix!");
    }
}
