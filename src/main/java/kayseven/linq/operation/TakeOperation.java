/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kayseven.linq.LINQOperation;

/**
 *
 * @author K7
 * @param <E>
 */
public class TakeOperation<E> extends LINQOperation<E> {

    private int idx;
    private final int count;

    public TakeOperation(Iterator<E> iterator, int count) {
        super(iterator);
        this.count = count;
        idx = 0;
    }

    @Override
    public boolean hasNext() {
        return idx < count && iterator.hasNext();
    }

    @Override
    public E next() {
        if (++idx > count) {
            throw new NoSuchElementException();
        }

        return iterator.next();
    }

}
