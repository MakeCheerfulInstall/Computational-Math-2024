import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterfacesAndExceptions {
    //    class Exception1 extends Exception {}
//
//    class Exception2 extends Exception {}
//
//    interface A1 {
//        Collection foo() throws Exception1;
//    }
//
//    interface A2 {
//        Set foo() throws Exception2;
//    }
//
//    class A1A2 implements A1, A2 {
//        public Set foo() throws Exception {
//
//        }
//    }
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>();
        IntStream.range(0, 100).filter(i -> i % 2 == 0).peek(arr::add).forEach(System.out::println);
//        Arrays.stream(arr).forEach(System.out::println);
    }
}