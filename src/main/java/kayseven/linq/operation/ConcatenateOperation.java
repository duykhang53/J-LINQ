/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation;

import java.util.Iterator;
import kayseven.linq.LINQOperation;

/**
 *
 * @author K7
 * @param <E>
 */
public class ConcatenateOperation<E> extends LINQOperation<E> {
    private final Iterator<E> secondIterator;

    public ConcatenateOperation(Iterator<E> iterator, Iterator<E> secondIterator) {
        super(iterator);
        this.secondIterator = secondIterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || secondIterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.hasNext() ? iterator.next() : secondIterator.next();
    }

}
