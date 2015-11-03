package book_catalogue;

import java.util.List;
import java.util.LinkedList;
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

        if (maxLength == 0) return 0;

        for (int i = 0; i < maxLength; i++) {
            int compareResult = array1[i].compareTo(array2[i]);

            if (compareResult != 0) return compareResult;
        }

        // The first few elements of the longer array
        // are equal to the shorter array, so assume the longer array
        // is 'larger'
        return array1.length - array2.length;
    }

    public static <E> void spliceIntoList(List<E> list, int startIndex, int endIndex, E replacementElement) {
        LinkedList<E> l = new LinkedList<E>();
        l.add(replacementElement);
        Utils.spliceIntoList(list, startIndex, endIndex, l);
    }

    public static <E> void spliceIntoList(List<E> list, int startIndex, int endIndex, List<E> replacementElements) {
        for (int i = startIndex; i < endIndex + 1; i++) {
            list.remove(startIndex);
        }

        list.addAll(startIndex, replacementElements);
    }
}

