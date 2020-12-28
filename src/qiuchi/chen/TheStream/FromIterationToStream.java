package qiuchi.chen.TheStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class FromIterationToStream {
    public static void main(String[] args) {


        ArrayList<Integer> arrList = new ArrayList<>(10);
        Integer[] ordinaryArr = new Integer[10];
        for (int i = 0; i < 10; i++) {
            ordinaryArr[i] = i;
            arrList.add(i);
        }
        var result = arrList.stream().filter((n) -> n > 5).count();
        var resultFromParallelProcessing = Arrays.stream(ordinaryArr).parallel().filter(n -> n < 5).count();
        var fromOrdinaryArr = Stream.of(ordinaryArr).filter(n -> n > 5).count();
        //集合能直接成流，数组要静态方法转换
        System.out.println(result);
        System.out.println(fromOrdinaryArr);
        Stream stream = Stream.generate(Math::random);


        class Obj {
            public int a = 1;
        }
        List<Obj> objList = new ArrayList<>();
        objList.add(new Obj());
        objList.add(new Obj());
        objList.stream().forEach(obj -> obj.a = 2);
        objList.stream().map(obj -> obj.a = 2);//<?>和foreach区别？
        //<!>改值可以
    }
}
