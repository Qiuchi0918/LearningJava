package qiuchi.chen.lambdaexpression;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class Usage {
    public static void main(String[] args) {

        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1;
            }
        };
        supplier = () -> {
            return 1;
        };

        Obj objArr[] = new Obj[]{};
        Arrays.sort(objArr, new Comparator<Obj>() {
            @Override
            public int compare(Obj o1, Obj o2) {
                return 0;
            }
        });//<!>三种相同，逐步简化
        Arrays.sort(args, (first, second) -> {
            return first.length() - second.length();
        });
        Arrays.sort(args, (first, second) -> first.length() - second.length());

        Arrays.sort(objArr, Comparator.comparingInt(value -> value.getSomeInteger()));
        Arrays.sort(objArr, Comparator.comparingInt(Obj::getSomeInteger));
        Arrays.sort(objArr, Comparator.comparingInt((Obj ob) -> {
            return 1;
        }));//这几种相同


        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.toString();
            }
        };
        listener = (ActionEvent e) -> {
            e.toString();
        };
        listener = ActionEvent::toString;//这三种相同
        //这就是所谓的函数式接口(单一目标的接口)，所以可以用lambda表达式
    }
}

class Obj {
    public int getSomeInteger() {
        return 1;
    }
}
