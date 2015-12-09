package book_catalogue;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

public class Utils {
    public static <T> String join(T[] array, String separator) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            str.append(array[i]);

            if (i < array.length - 1) {
                str.append(separator);
            }
        }

        return str.toString();
    }

    public static <T extends Comparable<? super T>> int compareArrays(T[] array1, T[] array2) {
        int maxLength = array1.length < array2.length ? array1.length : array2.length;

        for (int i = 0; i < maxLength; i++) {
            int compareResult = array1[i].compareTo(array2[i]);

            if (compareResult != 0) return compareResult;
        }

        // By this point, the first few elements of the longer array
        // are equal to the shorter array, so assume the longer array
        // is 'larger'
        return array1.length - array2.length;
    }

    public static <E> Collection<E> union(E[]... arrays) {
        List<E> list = new ArrayList<E>();

        for (E[] array : arrays) {
            for (E elem : array) {
                if (!list.contains(elem)) {
                    list.add(elem);
                }
            }
        }

        return list;
    }

    public static <E> List<E> subList(List<E> list, int startIndex, int endIndex) {
        List<E> l = new LinkedList<E>();

        for (int i = startIndex; i <= endIndex; i++) {
            // Note: object references are maintained
            l.add(list.get(i));
        }

        return l;
    }

    public static int compareNumbers(Number n1, Number n2) {
        // Compares two Number objects,
        // using the first object to determine the type to compare by
        if (n1 instanceof Byte) {
            return ((Byte)n1).compareTo(new Byte(n2.byteValue()));
        } else if (n1 instanceof Short) {
            return ((Short)n1).compareTo(new Short(n2.shortValue()));
        } else if (n1 instanceof Integer) {
            return ((Integer)n1).compareTo(new Integer(n2.intValue()));
        } else if (n1 instanceof Long) {
            return ((Long)n1).compareTo(new Long(n2.longValue()));
        } else if (n1 instanceof Float) {
            return ((Float)n1).compareTo(new Float(n2.floatValue()));
        } else if (n1 instanceof Double) {
            return ((Double)n1).compareTo(new Double(n2.doubleValue()));
        } else {
            return 0;
        }
    }
}

