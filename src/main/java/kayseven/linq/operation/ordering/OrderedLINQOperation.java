/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation.ordering;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import kayseven.linq.LINQ;
import kayseven.linq.PropertyExpression;

/**
 *
 * @author K7
 * @param <E>
 * @param <EProperty>
 */
public abstract class OrderedLINQOperation<E, EProperty> implements Iterator<E> {

    protected final LinkedList<E> linkedList;
    protected final PropertyExpression<E, EProperty> expression;
    protected final Comparator<EProperty> comparator;

    public OrderedLINQOperation(LINQ<E> baseQ, PropertyExpression<E, EProperty> expression, Comparator<EProperty> comparator) {
        linkedList = baseQ.toLinkedList();
        this.expression = expression;
        this.comparator = comparator;
    }

    public final OrderedLINQ<E> getLINQ() {
        return new OrderedLINQ<E>(this);
    }

    @Override
    public boolean hasNext() {
        return !linkedList.isEmpty();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
