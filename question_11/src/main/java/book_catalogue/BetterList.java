package book_catalogue;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class BetterList<E> implements List<E> {
    private List<E> list;

    public BetterList(List<E> list) {
        this.list = list;
    }

    public boolean add(E e) {
        return this.list.add(e);
    }

    public void add(int index, E element) {
        this.list.add(index, element);
    }

    public boolean addAll(Collection<? extends E> c) {
        return this.list.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        return this.list.addAll(index, c);
    }

    public void clear() {
        this.list.clear();
    }

    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    public E get(int index) {
        return this.list.get(index);
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    public int indexOf(Object o) {
        return this.list.indexOf(o);
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public Iterator<E> iterator() {
        return this.list.iterator();
    }

    public int lastIndexOf(Object o) {
        return this.list.lastIndexOf(o);
    }

    public ListIterator<E> listIterator() {
        return this.list.listIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return this.list.listIterator(index);
    }

    public E remove(int index) {
        return this.list.remove(index);
    }

    public boolean remove(Object o) {
        return this.list.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    public E set(int index, E element) {
        return this.list.set(index, element);
    }

    public int size() {
        return this.list.size();
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return this.list.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        return this.list.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return this.list.toArray(a);
    }

    public void splice(int startIndex, int endIndex, E replacementElement) {
        LinkedList<E> l = new LinkedList<E>();
        l.add(replacementElement);
        this.splice(startIndex, endIndex, l);
    }

    public void splice(int startIndex, int endIndex, Collection<? extends E> replacementElements) {
        this.list.subList(startIndex, endIndex + 1).clear();
        this.list.addAll(startIndex, replacementElements);
    }

    public BetterList<E> take(int count) {
        return this.take(0, count);
    }

    public BetterList<E> take(int startIndex, int count) {
        List<E> sublist = new LinkedList<E>(this.list.subList(startIndex, startIndex + count));
        this.list.subList(startIndex, startIndex + count).clear();
        return new BetterList(sublist);
    }

    public BetterList<E> takeUntilFound(E elem) {
        int index = this.list.indexOf(elem);

        if (index == -1) {
            index = this.list.size() - 1;
        }

        return this.take(index + 1);
    }

    public E pop() {
        return this.pop(this.list.size() - 1);
    }

    // Semantic alias
    public E pop(int index) {
        return this.list.remove(index);
    }

    public E first() {
        return this.list.get(0);
    }

    public E last() {
        return this.list.get(this.list.size() - 1);
    }
}

