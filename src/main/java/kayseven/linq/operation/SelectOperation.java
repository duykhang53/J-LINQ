/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation;

import java.util.Iterator;
import kayseven.linq.LINQOperation;
import kayseven.linq.PropertyExpression;

/**
 *
 * @author K7
 * @param <E>
 * @param <EType>
 */
public class SelectOperation<E, EType> extends LINQOperation<EType> {
    private final Iterator<E> iterator;
    private final PropertyExpression<E, EType> expression;

    public SelectOperation(Iterator<E> iterator, PropertyExpression<E, EType> expression) {
        super(null);
        this.iterator = iterator;
        this.expression = expression;
    }

    @Override
    public EType next() {
        return expression.getValue(iterator.next());
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

}
