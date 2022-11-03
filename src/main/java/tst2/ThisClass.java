package tst2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ThisClass {

    static class Inner {
        Comparator<Integer> comparator;

        public void setComparator(Comparator<Integer> comparator) {
            this.comparator = comparator;
        }
    }

    void sort() {
        integers.sort(inner.comparator);
    }

    public ThisClass(Inner inner, List<Integer> integers) {
        this.inner = inner;
        this.integers = integers;
        inner.setComparator(new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                id++;
                System.out.println(ThisClass.this);
                System.out.println(id);
                return o1.compareTo(o2);
            }
        });
    }

    Inner inner;
    List<Integer> integers;
    int id;


    public static void main(String[] args) throws InterruptedException {
        Inner inner1 = new Inner();
        ArrayList<Integer> integers1 = new ArrayList<>();
        integers1.add(3);
        integers1.add(2);
        integers1.add(1);
        ThisClass thisClass = new ThisClass(inner1, integers1);
        System.out.println(thisClass);
        thisClass.sort();
    }
}
