/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kayseven.linq.LINQOperation;
import kayseven.linq.Predicate;

/**
 *
 * @author K7
 * @param <E>
 */
public class SkipWhileOperation<E> extends LINQOperation<E> {
    private E next;

    public SkipWhileOperation(Iterator<E> iterator, Predicate<E> predicate) {
        super(iterator);
        while (iterator.hasNext()) {
            next = iterator.next();
            if (!predicate.test(next)) {
                hasNext = true;
                break;
            }
        }
    }

    @Override
    public E next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }

        E current = next;
        if (hasNext = iterator.hasNext()) {
            next = iterator.next();
        }

        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
