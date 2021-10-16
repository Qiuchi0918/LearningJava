package qiuchi.chen.basic;

import java.util.WeakHashMap;

public class AboutWeakReference {

    public static void main(String[] args) {

        WeakHashMap<String, String> map = new WeakHashMap<>();

        String a = "asdfasdf";
        String b = "asdfasd"+'f';
        String c = b.intern();
        System.out.println();
    }
}
