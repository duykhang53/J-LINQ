package kayseven.linq.operation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import kayseven.linq.EqualityComparer;
import kayseven.linq.LINQOperation;

/**
 *
 * @author K7
 * @param <E>
 */
public class DistinctOperation<E> extends LINQOperation<E> {
    private E next;
    private final LinkedList<E> iteratedElement;
    private final EqualityComparer<E> equalityComparer;

    public DistinctOperation(Iterator<E> iterator, EqualityComparer<E> equalityComparer) {
        super(iterator);
        iteratedElement = new LinkedList<E>();
        this.equalityComparer = equalityComparer;
        hasNext = iterator.hasNext();

        if (hasNext) {
            next = iterator.next();
            iteratedElement.add(next);
        }
    }

    private boolean isContained(E element) {
        for (E e : iteratedElement) {
            if (equalityComparer.isEquals(e, element)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public E next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }

        E current = next;

        while (iterator.hasNext()) {
            next = iterator.next();

            if (!isContained(next)) {
                iteratedElement.add(next);
                return current;
            }
        }

        hasNext = false;
        return current;
    }

}
