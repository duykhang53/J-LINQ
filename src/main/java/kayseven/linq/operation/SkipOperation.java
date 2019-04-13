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
public class SkipOperation<E> extends LINQOperation<E> {
    private final int offset;

    public SkipOperation(Iterator<E> iterator, int offset) {
        super(iterator);
        this.offset = offset;
        for (int i = 0; i < offset && iterator.hasNext(); i++) {
            iterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }

}
