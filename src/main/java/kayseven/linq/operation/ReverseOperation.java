/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import kayseven.linq.LINQOperation;

/**
 *
 * @author K7
 * @param <E>
 */
public class ReverseOperation<E> extends LINQOperation<E> {
    private final Stack<E> stack = new Stack<E>();

    public ReverseOperation(Iterator<E> iterator) {
        super(iterator);
        while (iterator.hasNext()) {
            E next = iterator.next();
            stack.push(next);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public E next() {
        if (stack.isEmpty()) {
            throw new NoSuchElementException();
        }

        return stack.pop();
    }

}
