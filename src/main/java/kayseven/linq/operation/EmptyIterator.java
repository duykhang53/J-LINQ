/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author K7
 */
public class EmptyIterator<E> implements Iterator<E> {

    public static <E> EmptyIterator<E> create() {
        return new EmptyIterator<E>();
    }
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public E next() {
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
