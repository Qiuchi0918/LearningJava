package qiuchi.chen.generictype;

import java.util.*;


class Collection {
    public static void main(String[] args) {
        ArrayList<Integer> arrLst = new ArrayList<>(10);
        arrLst.iterator().forEachRemaining(Integer::notify);
        arrLst.listIterator().add(1);
        HashSet<Integer> set = new HashSet<>();
        set.iterator();//无序所以没有listIterator
    }
}
