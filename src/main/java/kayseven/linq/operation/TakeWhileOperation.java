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
public class TakeWhileOperation<E> extends LINQOperation<E> {
    private E next;
    private final Predicate<E> predicate;

    public TakeWhileOperation(Iterator<E> iterator, Predicate<E> predicate) {
        super(iterator);
        this.predicate = predicate;
        if (iterator.hasNext()) {
            next = iterator.next();
            hasNext = predicate.test(next);
        }
    }

    @Override
    public E next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }

        E current = next;
        if (iterator.hasNext()) {
            next = iterator.next();
            hasNext = predicate.test(next);
        } else {
            hasNext = false;
        }

        return current;
    }

}
