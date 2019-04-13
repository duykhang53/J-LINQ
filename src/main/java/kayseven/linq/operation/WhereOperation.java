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
 */
public class WhereOperation<E> extends LINQOperation<E> {
    private E next;
    private Predicate<E> predicate;

    public WhereOperation(Iterator<E> iterator, Predicate<E> predicate) {
        super(iterator);
        this.predicate = predicate;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (predicate.test(next)) {
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

        while (iterator.hasNext()) {
            next = iterator.next();
            if (predicate.test(next)) {
                return current;
            }
        }

        hasNext = false;
        return current;
    }

}
