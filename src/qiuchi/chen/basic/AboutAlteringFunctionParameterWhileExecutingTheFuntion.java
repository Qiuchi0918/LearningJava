//Some people think that the wrapper classes can be used to implement methods that can modify
//numeric parameters. However, that is not correct. Recall from Chapter 4 that it is impossible to
//write a Java method that increments an integer parameter because parameters to Java methods
//are always passed by value.
//public static void triple(int x) // won't work
//{
//    x = 3 * x; // modifies local variable
//}
//Could we overcome this by using an Integer instead of an int?
//public static void triple(Integer x) // won't work
//{
//    . . .
//}
//The problem is that Integer objects are immutable: The information contained inside the
//wrapper can’t change. You cannot use these wrapper classes to create a method that modifies
//numeric parameters.
//If you do want to write a method to change numeric parameters, you can use one of the holder
//types defined in the org.omg.CORBA package: IntHolder, BooleanHolder, and so on. Each holder
//type has a public (!) field value through which you can access the stored value.
//public static void triple(IntHolder x)
//{
//    x.value = 3 * x.value;
//}

//或者也可以传数组
//<?>常用方法是？

package qiuchi.chen.basic;

class IntHolder {
    public int value = 0;
}

class Entry {
    public static void main(String[] args) {
        IntHolder holder = new IntHolder();
        int[] arr = new int[]{0};
        for (int i = 0; i < 10; i++) {
            altering(holder);
            altering(arr);
        }
        //System.out.println(holder.value);
        System.out.println(arr[0]);
    }

    static void altering(IntHolder holder) {
        holder.value++;
    }

    static void altering(int[] value) {
        value[0]++;
    }
}